package com.ethan.ucenter.service;

import com.ethan.common.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * @author Ethan 2023/2/12
 */
public interface ISendService {
    Future<ResponseResult> sendEmailCode(String email, int type,HttpServletRequest request);
}
