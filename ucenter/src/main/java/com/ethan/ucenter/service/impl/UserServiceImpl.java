package com.ethan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.RedisUtil;
import com.ethan.ucenter.config.BaseConfig;
import com.ethan.ucenter.mapper.UserMapper;
import com.ethan.ucenter.pojo.po.SignInInfo;
import com.ethan.ucenter.pojo.po.User;
import com.ethan.ucenter.pojo.po.UserInfo;
import com.ethan.ucenter.pojo.vo.LoginVO;
import com.ethan.ucenter.pojo.vo.PageVO;
import com.ethan.ucenter.pojo.vo.SignInVO;
import com.ethan.ucenter.pojo.vo.UserAdminVO;
import com.ethan.ucenter.pojo.vo.UserVO;
import com.ethan.ucenter.service.ICheckService;
import com.ethan.ucenter.service.ISignInInfoService;
import com.ethan.ucenter.service.IUserInfoService;
import com.ethan.ucenter.service.IUserService;
import com.ethan.ucenter.utils.ELog;
import com.ethan.ucenter.utils.JwtUtil;
import com.ethan.ucenter.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisUtil mRedisUtil;
    @Autowired
    private ICheckService mCheckService;
    @Autowired
    private BCryptPasswordEncoder mBCryptPasswordEncoder;
    @Autowired
    private IUserInfoService mUserInfoService;
    @Autowired
    private ISignInInfoService mSignInInfoService;

    @Override
    public ResponseResult signIn(String emailCode, SignInVO signInVO, HttpServletRequest request) {
        ELog.DEBUG("注册请求 ===> 携带验证码 " + emailCode + " 请求信息 " + signInVO.toString());

        // 校验内容
        if (TextUtil.isEmpty(signInVO.getEmail())) {
            return new ResponseResult(ResponseState.SIGNIN_INFO_ERROR.setMsg("邮箱不可以为空"));
        }
        if (TextUtil.isEmpty(signInVO.getUserName())) {
            return new ResponseResult(ResponseState.SIGNIN_INFO_ERROR.setMsg("用户名不可以为空"));
        }
        if (TextUtil.isEmpty(signInVO.getPassword())) {
            return new ResponseResult(ResponseState.SIGNIN_INFO_ERROR.setMsg("密码不可以为空"));
        }
        // 校验密码传输过程是否加密
        if (signInVO.getPassword().length() != 32) {
            return new ResponseResult(ResponseState.SIGNIN_INFO_ERROR.setMsg("请使用 MD5 对密码进行加密"));
        }
        // 校验验证码
        String RedisEmailCode = (String) mRedisUtil.get(BaseConfig.REDIS_SIGNIN_EMAIL_CODE_PREFIX + signInVO.getEmail());
        if (TextUtil.isEmpty(RedisEmailCode)) {
            return new ResponseResult(ResponseState.MSG_CODE_OUT_OF_DATE);
        } else if (!RedisEmailCode.equals(emailCode)) {
            return new ResponseResult(ResponseState.MSG_CODE_ERROR);
        }
        // 验证通过就删掉
        mRedisUtil.del(BaseConfig.REDIS_SIGNIN_EMAIL_CODE_PREFIX + signInVO.getEmail());

        // 邮箱在发送验证码时已经判重，不必重复
        // 用户名判重
        if (mCheckService.checkUserName(signInVO.getUserName())) {
            return new ResponseResult(ResponseState.USERNAME_EXIST);
        }

        // 操作数据库，加入新用户
        User newUser = new User();
        newUser.setUserName(signInVO.getUserName());
        // 密码再次加密后再存储
        String encodePassword = mBCryptPasswordEncoder.encode(signInVO.getPassword());
        newUser.setPassword(encodePassword);
        ELog.DEBUG("加密后密码 " + encodePassword);
        newUser.setStatus(BaseConfig.DB_USER_STATUS_NORMAL);
        newUser.setSalt(IdWorker.getIdStr());
        getBaseMapper().insert(newUser);

        // 操作数据库，加入新用户信息
        UserInfo newInfo = new UserInfo();
        newInfo.setUserId(newUser.getUserId());
        newInfo.setEmail(signInVO.getEmail());
        mUserInfoService.getBaseMapper().insert(newInfo);

        // 操作数据库，加入注册信息
        SignInInfo signInInfo = new SignInInfo();
        signInInfo.setUserId(newUser.getUserId());
        signInInfo.setSignInIp(request.getRemoteAddr());
        // signInInfo.setRanking();
        signInInfo.setSignInTime(LocalDateTime.now());
        mSignInInfoService.getBaseMapper().insert(signInInfo);

        return new ResponseResult(ResponseState.SIGNIN_SUCCESS);
    }

    @Override
    public ResponseResult logIn(String appKey, LoginVO loginVO, HttpServletRequest request) {
        ELog.INFO("登录请求 ===> 携带appKey " + appKey + " 请求信息 " + loginVO.toString());

        // 校验内容
        if (TextUtil.isEmpty(loginVO.getUserName())) {
            return new ResponseResult(ResponseState.LOGIN_INFO_ERROR.setMsg("用户名不可以为空"));
        }
        if (TextUtil.isEmpty(loginVO.getPassword())) {
            return new ResponseResult(ResponseState.LOGIN_INFO_ERROR.setMsg("密码不可以为空"));
        }
        // 校验密码传输过程是否加密
        if (loginVO.getPassword().length() != 32) {
            return new ResponseResult(ResponseState.LOGIN_INFO_ERROR.setMsg("请使用 MD5 对密码进行加密"));
        }

        // 校验用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", loginVO.getUserName());
        User user = getBaseMapper().selectOne(wrapper);
        if (user == null) {
            return new ResponseResult(ResponseState.LOGIN_FAILED.setMsg("该用户不存在"));
        }
        if (user.getStatus() == BaseConfig.DB_USER_STATUS_DISABLED) {
            return new ResponseResult(ResponseState.LOGIN_FAILED.setMsg("该用户目前处于封禁期"));
        }
        if (!mBCryptPasswordEncoder.matches(loginVO.getPassword(), user.getPassword())) {
            return new ResponseResult(ResponseState.LOGIN_FAILED.setMsg("密码错误"));
        }

        // 创建 Token (Payload 只放 appKey，不能放盐会泄露)
        Map<String, Object> claims = new HashMap<>();
        claims.put(BaseConfig.TOKEN_PAYLOAD_APPKEY, appKey);
        String token = JwtUtil.createToken(claims, user.getSalt());
        ELog.INFO("Token ===> " + token);

        // 放入 Redis (Token ---> UID)
        mRedisUtil.set(BaseConfig.REDIS_UID_PREFIX + token, String.valueOf(user.getUserId()), BaseConfig.REDIS_TOKEN_EXPIRE);
        // 放入 Redis (Token ---> 盐)
        mRedisUtil.set(BaseConfig.REDIS_SALT_PREFIX + token, user.getSalt(), BaseConfig.REDIS_TOKEN_EXPIRE);

        // 最佳实践：返回 Token 的 MD5 加密值，Redis 里放 TokenMD5--->Token，Token 的 Payload 里放 UID 和 APPKey
        String tokenMD5 = DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
        ELog.INFO("TokenMD5 ===> " + tokenMD5);

        // 但我没时间写那么复杂，所以暂时直接把 Token 扔回去：）
        return new ResponseResult(ResponseState.LOGIN_SUCCESS, token);
    }

    @Override
    public ResponseResult getInfo(String token, HttpServletRequest request) {
        ELog.INFO("获取用户信息 ===> " + token);

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 联立查询
        UserVO userVO = getBaseMapper().selectUserAllInfoById(uid);

        // 返回数据
        return new ResponseResult(ResponseState.SUCCESS, userVO);
    }

    @Override
    public ResponseResult editInfo(String token, UserVO userVO, HttpServletRequest request) {
        ELog.INFO("修改用户信息 ===> " + token);

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 用户名和邮箱不允许更改，所以只需要对用户信息表进行更新即可
        UserInfo info = userVO.toUserInfoPO(Long.parseLong(uid));
        UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", uid);
        mUserInfoService.getBaseMapper().update(info, wrapper);
        return new ResponseResult(ResponseState.SUCCESS);
    }

    @Override
    public ResponseResult editPassword(String token, String emailCode, SignInVO signInVO, HttpServletRequest request) {
        ELog.INFO("修改密码 ===> " + token);

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 内容检查
        if (TextUtil.isEmpty(signInVO.getUserName())) {
            return new ResponseResult(ResponseState.FAIL.setMsg("用户名不可以为空"));
        }
        if (TextUtil.isEmpty(signInVO.getPassword())) {
            return new ResponseResult(ResponseState.FAIL.setMsg("密码不可以为空"));
        }
        // 校验密码传输过程是否加密
        if (signInVO.getPassword().length() != 32) {
            return new ResponseResult(ResponseState.FAIL.setMsg("请使用 MD5 对密码进行加密"));
        }

        // 校验验证码
        String RedisEmailCode = (String) mRedisUtil.get(BaseConfig.REDIS_PWD_EMAIL_CODE_PREFIX + signInVO.getEmail());
        if (TextUtil.isEmpty(RedisEmailCode)) {
            return new ResponseResult(ResponseState.MSG_CODE_OUT_OF_DATE);
        } else if (!RedisEmailCode.equals(emailCode)) {
            return new ResponseResult(ResponseState.MSG_CODE_ERROR);
        }
        // 验证通过就删掉
        mRedisUtil.del(BaseConfig.REDIS_PWD_EMAIL_CODE_PREFIX + signInVO.getEmail());

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 写入数据库
        User user = new User();
        user.setUserId(Long.parseLong(uid));
        // 密码再次加密后再存储
        String encodePassword = mBCryptPasswordEncoder.encode(signInVO.getPassword());
        user.setPassword(encodePassword);
        ELog.DEBUG("加密后密码 " + encodePassword);
        getBaseMapper().updateById(user);

        // 退出登录
        return exit(token, request);
    }

    @Override
    public ResponseResult exit(String token, HttpServletRequest request) {
        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }
        // 从 Redis 中删除
        mRedisUtil.del(BaseConfig.REDIS_UID_PREFIX + token);
        mRedisUtil.del(BaseConfig.REDIS_SALT_PREFIX + token);
        return new ResponseResult(ResponseState.SUCCESS);
    }

    @Override
    public PageVO<UserAdminVO> listUser(int page, String phone, String email, String username, String id, String status) {
        // 数据库查询
        if (page < 1) {
            page = 1;
        }
        int size = BaseConfig.DB_PAGE_SIZE;
        int offset = (page - 1) * size;
        List<UserAdminVO> userVOS = getBaseMapper().selectAllUser(size, offset, page, phone, email, username, id, status);
        ELog.DEBUG("分页查询结果 " + userVOS.toString());

        // 组装业务对象
        int totalCount = getBaseMapper().selectList(null).size();
        PageVO<UserAdminVO> pageVO = new PageVO<>();
        pageVO.setList(userVOS);
        pageVO.setCurrentPage(page);
        pageVO.setListSize(userVOS.size());
        float tempTotalPage = (totalCount * 1.0f / size) + 0.5f;
        int totalPage = Math.round(tempTotalPage);
        pageVO.setTotalPage(totalPage);
        pageVO.setTotalCount(totalCount);
        pageVO.setHasPrePage(page != 1);
        pageVO.setHasNextPage(page != totalPage);

        return pageVO;
    }
}
