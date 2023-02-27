package com.ethan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.RedisUtil;
import com.ethan.ucenter.config.BaseConfig;
import com.ethan.ucenter.pojo.po.AppInfo;
import com.ethan.ucenter.pojo.po.Role;
import com.ethan.ucenter.pojo.po.User;
import com.ethan.ucenter.pojo.vo.PageVO;
import com.ethan.ucenter.pojo.vo.UserAdminVO;
import com.ethan.ucenter.service.IAdminService;
import com.ethan.ucenter.service.IAppInfoService;
import com.ethan.ucenter.service.ICheckService;
import com.ethan.ucenter.service.IRoleService;
import com.ethan.ucenter.service.IUserService;
import com.ethan.common.utils.ELog;
import com.ethan.ucenter.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ethan 2023/2/16
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private ICheckService mCheckService;
    @Autowired
    private RedisUtil mRedisUtil;
    @Autowired
    private IRoleService mRoleService;
    @Autowired
    private IUserService mUserService;
    @Autowired
    private IAppInfoService mAppInfoService;

    @Override
    public ResponseResult getUserAmount(String token, HttpServletRequest request) {

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 检查是否是 EUC 管理员
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("app_id", BaseConfig.DB_ROLE_APP_ID_EUC);
        wrapper.ge("role", BaseConfig.DB_ROLE_ROLE_ADMIN);
        if (!mRoleService.getBaseMapper().exists(wrapper)) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        int amount = mUserService.getBaseMapper().selectList(null).size();
        return new ResponseResult(ResponseState.SUCCESS, amount);
    }

    /**
     * 只检查权限，逻辑交给 UserService 实现
     */
    @Override
    public ResponseResult listUser(String token, int page, String phone, String email, String username, String id, String status, HttpServletRequest request) {
        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // EUC权限检查
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("app_id", BaseConfig.DB_ROLE_APP_ID_EUC);
        wrapper.ge("role", BaseConfig.DB_ROLE_ROLE_ADMIN);
        if (!mRoleService.getBaseMapper().exists(wrapper)) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        // 返回联立之后的结果
        PageVO<UserAdminVO> pageVO = mUserService.listUser(page, phone, email, username, id, status);
        return new ResponseResult(ResponseState.SUCCESS, pageVO);
    }

    /**
     * 接入方管理员传自己的 Token 上来，这里就能解析出来自于哪个接入方 APP，然后修改用户在该应用中管理权限
     */
    @Override
    public ResponseResult changeRole(String token, long userId, int identity, HttpServletRequest request) {

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 解析 Token 拿到 AppKey
        String salt = (String) mRedisUtil.get(BaseConfig.REDIS_SALT_PREFIX + token);
        Claims claims = JwtUtil.parseJWT(token, salt);
        String appKey = claims.get(BaseConfig.TOKEN_PAYLOAD_APPKEY, String.class);
        ELog.DEBUG("解析 Token 得到的 appKey " + appKey);
        // 由 AppKey 拿到 AppId
        QueryWrapper<AppInfo> appInfoWrapper = new QueryWrapper<>();
        appInfoWrapper.eq("app_key", appKey);
        AppInfo appInfo = mAppInfoService.getBaseMapper().selectOne(appInfoWrapper);

        // 从 Redis 拿调用者 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 调用者权限检查，判断调用者是不是该应用的管理员
        QueryWrapper<Role> adminRoleWrapper = new QueryWrapper<>();
        adminRoleWrapper.eq("user_id", uid);
        adminRoleWrapper.eq("app_id", appInfo.getAppId());
        adminRoleWrapper.ge("role", BaseConfig.DB_ROLE_ROLE_ADMIN);
        if (!mRoleService.getBaseMapper().exists(adminRoleWrapper)) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        // 组装新权限对象
        Role role = new Role();
        role.setUserId(userId);
        role.setRole(identity);
        role.setAppId(appInfo.getAppId());

        // 目标用户权限检查，看看是否已经是该应用管理员，要被撤职或升官的情况
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("user_id", userId);
        roleWrapper.eq("app_id", appInfo.getAppId());
        roleWrapper.ge("role", BaseConfig.DB_ROLE_ROLE_ADMIN);
        if (mRoleService.getBaseMapper().exists(roleWrapper)) {
            // 如果目标用户已经是该应用管理员，更新信息即可
            UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId);
            updateWrapper.eq("app_id", appInfo.getAppId());
            mRoleService.getBaseMapper().update(role, updateWrapper);
        } else {
            // 如果目标用户还不是该应用管理员，插入信息即可
            mRoleService.getBaseMapper().insert(role);
        }

        return new ResponseResult(ResponseState.SUCCESS);
    }

    @Override
    public ResponseResult disableUser(String token, long userId, int status, HttpServletRequest request) {

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 解析 Token 拿到 AppKey
        String salt = (String) mRedisUtil.get(BaseConfig.REDIS_SALT_PREFIX + token);
        Claims claims = JwtUtil.parseJWT(token, salt);
        String appKey = claims.get(BaseConfig.TOKEN_PAYLOAD_APPKEY, String.class);
        ELog.DEBUG("解析 Token 得到的 appKey " + appKey);
        // 由 AppKey 拿到 AppId
        QueryWrapper<AppInfo> appInfoWrapper = new QueryWrapper<>();
        appInfoWrapper.eq("app_key", appKey);
        AppInfo appInfo = mAppInfoService.getBaseMapper().selectOne(appInfoWrapper);

        // 从 Redis 拿调用者 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 调用者权限检查，判断调用者是不是该应用的管理员
        QueryWrapper<Role> adminRoleWrapper = new QueryWrapper<>();
        adminRoleWrapper.eq("user_id", uid);
        adminRoleWrapper.eq("app_id", appInfo.getAppId());
        adminRoleWrapper.ge("role", BaseConfig.DB_ROLE_ROLE_ADMIN);
        if (!mRoleService.getBaseMapper().exists(adminRoleWrapper)) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        User user = new User();
        user.setUserId(userId);
        user.setStatus(status);
        mUserService.getBaseMapper().updateById(user);

        return new ResponseResult(ResponseState.SUCCESS);
    }

    @Override
    public ResponseResult deleteUser(String token, long userId, HttpServletRequest request) {

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 解析 Token 拿到 AppKey
        String salt = (String) mRedisUtil.get(BaseConfig.REDIS_SALT_PREFIX + token);
        Claims claims = JwtUtil.parseJWT(token, salt);
        String appKey = claims.get(BaseConfig.TOKEN_PAYLOAD_APPKEY, String.class);
        ELog.DEBUG("解析 Token 得到的 appKey " + appKey);
        // 由 AppKey 拿到 AppId
        QueryWrapper<AppInfo> appInfoWrapper = new QueryWrapper<>();
        appInfoWrapper.eq("app_key", appKey);
        AppInfo appInfo = mAppInfoService.getBaseMapper().selectOne(appInfoWrapper);

        // 从 Redis 拿调用者 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);

        // 调用者权限检查，判断调用者是不是该应用的管理员
        QueryWrapper<Role> adminRoleWrapper = new QueryWrapper<>();
        adminRoleWrapper.eq("user_id", uid);
        adminRoleWrapper.eq("app_id", appInfo.getAppId());
        adminRoleWrapper.ge("role", BaseConfig.DB_ROLE_ROLE_ADMIN);
        if (!mRoleService.getBaseMapper().exists(adminRoleWrapper)) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        User user = new User();
        user.setUserId(userId);
        mUserService.getBaseMapper().deleteById(user);

        return new ResponseResult(ResponseState.SUCCESS);
    }
}
