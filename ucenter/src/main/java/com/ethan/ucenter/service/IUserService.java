package com.ethan.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.ucenter.pojo.po.User;
import com.ethan.ucenter.pojo.vo.LoginVO;
import com.ethan.ucenter.pojo.vo.PageVO;
import com.ethan.ucenter.pojo.vo.SignInVO;
import com.ethan.ucenter.pojo.vo.UserAdminVO;
import com.ethan.ucenter.pojo.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
public interface IUserService extends IService<User> {

    ResponseResult signIn(String emailCode, SignInVO signInVO, HttpServletRequest request);

    ResponseResult logIn(String appKey, LoginVO loginVO, HttpServletRequest request);

    ResponseResult getInfo(String token, HttpServletRequest request);

    ResponseResult editInfo(String token, UserVO userVO, HttpServletRequest request);

    ResponseResult editPassword(String token, String emailCode, SignInVO signInVO, HttpServletRequest request);

    ResponseResult exit(String token, HttpServletRequest request);

    PageVO<UserAdminVO> listUser(int page, String phone, String email, String username, String id, String status);
}
