package com.ethan.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;

/**
 * @author Ethan 2023/2/14
 */
public interface ICheckService {
    boolean checkEmail(String email);

    boolean checkUserName(String name);

    /**
     * Token 合法性检查
     * 1. 检查是否登录
     * 2. 检查 APP 是否接入 UC
     */
    boolean checkToken(String token);

    boolean checkApp(String appKey);

    String getAppKey(String token, String salt);
}
