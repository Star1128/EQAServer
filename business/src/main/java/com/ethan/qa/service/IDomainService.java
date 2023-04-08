package com.ethan.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.common.response.ResponseResult;
import com.ethan.qa.pojo.po.Domain;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
public interface IDomainService extends IService<Domain> {

    /**
     * 判断领域是否存在
     */
    boolean hasDomain(Long domainId);

    /**
     * 创建领域
     */
    ResponseResult createDomain(String name);

    /**
     * 删除领域
     */
    ResponseResult deleteDomain(Long id);

    /**
     * 领域列表
     */
    ResponseResult listDomain();

    String getDomainName(Long domainId);
}
