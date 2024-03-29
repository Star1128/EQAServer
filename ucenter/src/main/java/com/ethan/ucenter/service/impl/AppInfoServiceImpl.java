package com.ethan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.ELog;
import com.ethan.common.utils.RedisUtil;
import com.ethan.ucenter.config.BaseConfig;
import com.ethan.ucenter.mapper.AppInfoMapper;
import com.ethan.ucenter.pojo.po.AppInfo;
import com.ethan.ucenter.pojo.vo.AppInfoVO;
import com.ethan.ucenter.service.IAppInfoService;
import com.ethan.ucenter.service.ICheckService;
import com.ethan.ucenter.service.IRoleService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements IAppInfoService {

    @Autowired
    private ICheckService mCheckService;
    @Autowired
    private RedisUtil mRedisUtil;
    @Autowired
    private IRoleService mRoleService;

    @Override
    public ResponseResult register(String token, AppInfoVO appInfoVO) {
        ELog.INFO("申请接入 EUC ===> " + token);

        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);
        AppInfo appInfo = appInfoVO.toAppInfo(Long.parseLong(uid));
        // 生成 Appkey AppSecret
        String appKey = RandomStringUtils.randomAlphabetic(32);
        ELog.INFO("appKey ===> " + appKey);
        String appSecret = RandomStringUtils.randomAlphabetic(32);
        ELog.INFO("appSecret ===> " + appSecret);
        appInfo.setAppKey(appKey);
        appInfo.setAppSecret(appSecret);
        // TODO: 2023/2/16 判断是不是 Ethan 注册的，先默认 Ethan
        appInfo.setStatus(BaseConfig.DB_APP_STATUS_ETHAN);

        // 存入数据库
        getBaseMapper().insert(appInfo);

        // 申请者自动成为所注册应用的超级管理员
        mRoleService.addRole(appInfo.getAppId(), BaseConfig.DB_ROLE_ROLE_SUPER_ADMIN, Long.parseLong(uid));

        return new ResponseResult(ResponseState.SUCCESS, appKey);
    }

    @Override
    public ResponseResult getUID(String token) {
        // Token 合法性检查（其实在登录生成 Token 的时候已经检查过一次 APP 合法性了 hhh）
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);
        return new ResponseResult(ResponseState.THIRD_PARTY_ACCESS_SUCCESS, uid);
    }

    @Override
    public ResponseResult isAdmin(String token) {
        // Token 合法性检查
        if (!mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        }

        // 从 Redis 拿 UID
        Long uid = Long.parseLong((String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token));
        String salt = (String) mRedisUtil.get(BaseConfig.REDIS_SALT_PREFIX + token);
        String appKey = mCheckService.getAppKey(token, salt);
        Long appId = appKey2appId(appKey);

        if (mRoleService.checkRole(appId, uid)) {
            return ResponseResult.SUCCESS("该用户为应用（appId = " + appId + "）的管理员");
        }
        return ResponseResult.FAIL("该用户不是应用（appId = " + appId + "）的管理员");
    }

    @Override
    public Long appKey2appId(String appKey) {
        QueryWrapper<AppInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("app_key", appKey);
        AppInfo appInfo = getOne(wrapper);
        return appInfo.getAppId();
    }
}
