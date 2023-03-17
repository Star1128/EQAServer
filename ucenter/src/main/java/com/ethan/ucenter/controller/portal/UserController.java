package com.ethan.ucenter.controller.portal;

import com.ethan.common.response.ResponseResult;
import com.ethan.ucenter.pojo.vo.LoginVO;
import com.ethan.ucenter.pojo.vo.SignInVO;
import com.ethan.ucenter.pojo.vo.UserVO;
import com.ethan.ucenter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService mUserService;

    @PostMapping("/signin")
    public ResponseResult signIn(@RequestParam("email_code") String emailCode, @RequestBody SignInVO signInVO, HttpServletRequest request) {
        return mUserService.signIn(emailCode, signInVO, request);
    }

    @PostMapping("/login")
    public ResponseResult logIn(@RequestParam("app_key") String appKey, @RequestBody LoginVO loginVO, HttpServletRequest request) {
        return mUserService.logIn(appKey, loginVO, request);
    }

    @GetMapping("/info")
    public ResponseResult getInfo(@RequestParam("token") String token, HttpServletRequest request) {
        return mUserService.getInfo(token, request);
    }

    @PostMapping("/info/edit")
    public ResponseResult editInfo(@RequestParam("token") String token, @RequestBody UserVO userVO, HttpServletRequest request) {
        return mUserService.editInfo(token, userVO, request);
    }

    @PostMapping("/find-back-password")
    public ResponseResult editPassword(@RequestParam("token") String token, @RequestParam("email_code") String emailCode, @RequestBody SignInVO signInVO, HttpServletRequest request) {
        return mUserService.editPassword(token, emailCode, signInVO, request);
    }

    @PostMapping("/exit")
    public ResponseResult exit(@RequestParam("token") String token, HttpServletRequest request) {
        return mUserService.exit(token, request);
    }

    @GetMapping("/name/{userId}")
    public ResponseResult getName(@PathVariable("userId") Long userId) {
        return mUserService.getName(userId);
    }
}
