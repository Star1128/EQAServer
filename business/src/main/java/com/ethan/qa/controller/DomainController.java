package com.ethan.qa.controller;

import com.ethan.common.response.ResponseResult;
import com.ethan.qa.service.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/domain")
public class DomainController {

    @Autowired
    private IDomainService mDomainService;

    @PostMapping("/create")
    public ResponseResult createDomain(@RequestParam("domain_name") String name) {
        return mDomainService.createDomain(name);
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteDomain(@RequestParam("domain_id") Long id) {
        return mDomainService.deleteDomain(id);
    }

    @GetMapping("/list")
    public ResponseResult listDomain(){
        return mDomainService.listDomain();
    }
}
