package com.ethan.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;

/**
 * @author Ethan 2023/2/14
 */
public interface ICheckService {
    boolean checkEmail(String email);

    boolean checkUserName(String name);

    boolean checkToken(String token);

    boolean checkApp(String appKey);
}
