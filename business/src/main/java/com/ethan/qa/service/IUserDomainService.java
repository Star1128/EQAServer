package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.UserDomain;

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

    void joinDomain(Long uid, Long domainId);

    /**
     * 加经验
     */
    void updateExp(long uid, long did, int exp);

    /**
     * 获取用户在某领域的经验
     */
    int getExp(Long userId, long domainId);
}
