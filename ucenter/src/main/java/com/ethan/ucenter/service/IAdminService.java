package com.ethan.ucenter.service;

import com.ethan.common.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ethan 2023/2/16
 */
public interface IAdminService {

    ResponseResult getUserAmount(String token, HttpServletRequest request);

    ResponseResult listUser(String token, int page,String phone, String email, String username, String id, String status, HttpServletRequest request);

    ResponseResult changeRole(String token, long userId,int role,HttpServletRequest request);

    ResponseResult disableUser(String token, long userId, int status,HttpServletRequest request);

    ResponseResult deleteUser(String token, long userId, HttpServletRequest request);
}
