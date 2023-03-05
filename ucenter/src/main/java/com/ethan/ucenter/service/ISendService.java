package com.ethan.ucenter.service;

import com.ethan.common.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * @author Ethan 2023/2/12
 */
public interface ISendService {
    /**
     * 发送邮件（异步）
     * @param type 1 注册 2 找回密码
     */
    Future<ResponseResult> sendEmailCode(String email, int type,HttpServletRequest request);
}
