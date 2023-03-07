package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.UserDomain;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
public interface IUserDomainService extends IService<UserDomain> {

    /**
     * 获取用户所加入的领域信息
     */
    ResponseResult getInfo();

    /**
     * 获取用户领域数量
     */
    long userDomainAmount(long uid);

    /**
     * 加入领域
     */
    ResponseResult joinDomain(Long domainId);
}
