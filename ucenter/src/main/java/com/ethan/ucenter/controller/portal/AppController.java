package com.ethan.ucenter.controller.portal;

import com.ethan.common.response.ResponseResult;
import com.ethan.ucenter.pojo.vo.AppInfoVO;
import com.ethan.ucenter.service.IAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    IAppInfoService mAppInfoService;

    /**
     * 目前的注册 APP 流程是提交信息后生成 appKey 返回，暂时闲置 appSecret 字段
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestParam("token") String token, @RequestBody AppInfoVO appInfoVO) {
        return mAppInfoService.register(token, appInfoVO);
    }

    @GetMapping("/uid")
    public ResponseResult getUID(@RequestParam("token") String token){
        return mAppInfoService.getUID(token);
    }

    @GetMapping("/is-admin")
    public ResponseResult isAdmin(@RequestParam("token") String token){
        return mAppInfoService.isAdmin(token);
    }
}
