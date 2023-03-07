package com.ethan.qa.controller;

import com.ethan.common.response.ResponseResult;
import com.ethan.qa.service.IUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@RestController
@RequestMapping("/domain/user")
public class UserDomainController {

    @Autowired
    private IUserDomainService mUserDomainService;

    @GetMapping("/personal")
    public ResponseResult userDomain() {
        return mUserDomainService.getInfo();
    }

    @PutMapping("/join")
    public ResponseResult joinDomain(@RequestParam("domain_id") Long domainId) {
        return mUserDomainService.joinDomain(domainId);
    }
}
