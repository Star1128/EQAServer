package com.ethan.ucenter.controller.portal;

import com.ethan.common.response.ResponseResult;
import com.ethan.ucenter.service.ISendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * 负责发送消息
 *
 * @author Ethan 2023/2/11
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private ISendService mSendService;

    @GetMapping("/email_code")
    public ResponseResult sendEmailCode(@RequestParam("email") String email, @RequestParam("type") int type, HttpServletRequest request) throws ExecutionException, InterruptedException {
        // 发送验证码并存入 Redis
        return mSendService.sendEmailCode(email, type, request).get();
    }
}
