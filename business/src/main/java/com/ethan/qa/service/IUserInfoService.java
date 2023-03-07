package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.UserInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 获取余额
     */
    int getBalance(long uid);

    /**
     * 支付
     */
    boolean withdraw(long uid, int amount);

    /**
     * 入账
     */
    boolean deposit(long uid, int amount);

    /**
     * 用户全部信息概览
     */
    ResponseResult overviewInfo();
}
