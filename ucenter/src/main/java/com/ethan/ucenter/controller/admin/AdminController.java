package com.ethan.ucenter.controller.admin;

import com.ethan.common.response.ResponseResult;
import com.ethan.ucenter.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 给管理员返回的数据 UserAdminVO 包含了 UID，所以管理类接口都可以把 UID 当做参数。
 * 普通接入方获得的 UserVO 不包含 UID。
 *
 * @author Ethan 2023/2/16
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService mAdminService;

    @GetMapping("/user/amount")
    public ResponseResult getUserAmount(@RequestParam("token") String token, HttpServletRequest request) {
        return mAdminService.getUserAmount(token, request);
    }

    @GetMapping("/user/list/{page}")
    public ResponseResult listUser(@RequestParam(value = "token") String token, @PathVariable("page") int page, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "status", required = false) String status, HttpServletRequest request) {
        return mAdminService.listUser(token, page, phone, email, username, id, status, request);
    }

    @PostMapping("/user/role/{userId}")
    public ResponseResult changeRole(@RequestParam("token") String token, @PathVariable("userId") long userId, @RequestParam("role") int role, HttpServletRequest request) {
        return mAdminService.changeRole(token, userId, role, request);
    }

    @GetMapping("/user/disable/{userId}")
    public ResponseResult disableUser(@RequestParam("token") String token, @PathVariable("userId") long userId, @RequestParam("status") int status, HttpServletRequest request) {
        return mAdminService.disableUser(token,userId,status,request);
    }

    @GetMapping("/user/delete/{userId}")
    public ResponseResult deleteUser(@RequestParam("token") String token, @PathVariable("userId") long userId, HttpServletRequest request) {
        return mAdminService.deleteUser(token,userId,request);
    }
}
