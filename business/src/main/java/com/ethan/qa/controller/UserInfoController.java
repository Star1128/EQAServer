package com.ethan.qa.controller;

import com.ethan.common.response.ResponseResult;
import com.ethan.qa.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@RestController
@RequestMapping("/info")
public class UserInfoController {

    @Autowired
    private IUserInfoService mUserInfoService;

    @GetMapping("/overview")
    public ResponseResult overviewInfo(){
        return mUserInfoService.overviewInfo();
    }
}
