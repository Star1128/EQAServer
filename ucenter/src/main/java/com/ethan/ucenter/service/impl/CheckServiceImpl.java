package com.ethan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.common.utils.RedisUtil;
import com.ethan.ucenter.config.BaseConfig;
import com.ethan.ucenter.pojo.po.AppInfo;
import com.ethan.ucenter.pojo.po.User;
import com.ethan.ucenter.pojo.po.UserInfo;
import com.ethan.ucenter.service.IAppInfoService;
import com.ethan.ucenter.service.ICheckService;
import com.ethan.ucenter.service.IUserInfoService;
import com.ethan.ucenter.service.IUserService;
import com.ethan.ucenter.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务 Service
 * 公共业务 Service，所以在 Controller 再封装 RR
 *
 * @author Ethan 2023/2/14
 */
@Service
public class CheckServiceImpl implements ICheckService {

    @Autowired
    private IUserInfoService mUserInfoService;
    @Autowired
    private IUserService mUserService;
    @Autowired
    private IAppInfoService mAppInfoService;
    @Autowired
    private RedisUtil mRedisUtil;

    @Override
    public boolean checkEmail(String email) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return mUserInfoService.getBaseMapper().exists(wrapper);
    }

    @Override
    public boolean checkUserName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", name);
        return mUserService.getBaseMapper().exists(wrapper);
    }

    @Override
    public boolean checkToken(String token) {
        // 当且仅当 UID 和盐都存在时才有效
        String uid = (String) mRedisUtil.get(BaseConfig.REDIS_UID_PREFIX + token);
        String salt = (String) mRedisUtil.get(BaseConfig.REDIS_SALT_PREFIX + token);
        if (uid == null || salt == null) {
            return false;
        }
        // 解析 Token 拿到 AppKey，判断该 APP 是否接入 UC
        Claims claims = JwtUtil.parseJWT(token, salt);
        String appKey = claims.get(BaseConfig.TOKEN_PAYLOAD_APPKEY, String.class);
        return checkApp(appKey);
    }

    @Override
    public boolean checkApp(String appKey) {
        if (BaseConfig.SWITCH_APPS_AUTHENTICATION) {
            QueryWrapper<AppInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("app_key", appKey);
            return mAppInfoService.getBaseMapper().exists(wrapper);
        }
        return true;
    }
}
