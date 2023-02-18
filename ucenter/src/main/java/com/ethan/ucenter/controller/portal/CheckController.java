package com.ethan.ucenter.controller.portal;

import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.ucenter.service.ICheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 负责内容检查
 * 1. 获取图灵验证码（取消）
 * 2. 检查图灵验证码（取消）
 * 3. 检查邮箱是否已被注册
 * 4. 检查用户名是否已被注册
 * 5. 检查 Token 是否有效
 * 6. 检查用户是否是管理员
 *
 * @author Ethan 2023/2/11
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private ICheckService mCheckService;

    /**
     * 检查邮箱是否已被注册
     */
    @GetMapping("/email")
    public ResponseResult checkEmail(@RequestParam("email") String email) {
        if (mCheckService.checkEmail(email)) {
            return new ResponseResult(ResponseState.EMAIL_EXIST);
        } else {
            return new ResponseResult(ResponseState.SUCCESS.setMsg("邮箱未被注册"));
        }
    }

    /**
     * 检查用户名是否已被注册
     */
    @GetMapping("/username")
    public ResponseResult checkUserName(@RequestParam("userName") String userName) {
        if (mCheckService.checkUserName(userName)) {
            return new ResponseResult(ResponseState.USERNAME_EXIST);
        } else {
            return new ResponseResult(ResponseState.SUCCESS.setMsg("用户名未被注册"));
        }
    }

    /**
     * 检查 Token 是否有效，必须同时符合：
     * 1. Token 没过期
     * 2. 来源应用已在统一用户中心注册
     */
    @GetMapping("/token")
    public ResponseResult checkToken(@RequestParam("token") String token) {
        if (mCheckService.checkToken(token)) {
            return new ResponseResult(ResponseState.SUCCESS.setMsg("Token 有效"));
        } else {
            return new ResponseResult(ResponseState.FAIL.setMsg("Token 无效"));
        }
    }

    /**
     * 检查 APP 是否在用户中心注册过
     */
    @GetMapping("/app")
    public ResponseResult checkApp(@RequestParam("appkey") String appKey) {
        if (mCheckService.checkApp(appKey)) {
            return new ResponseResult(ResponseState.SUCCESS.setMsg("该应用已在EUC注册"));
        } else {
            return new ResponseResult(ResponseState.FAIL.setMsg("该应用未在EUC注册"));
        }
    }
}
