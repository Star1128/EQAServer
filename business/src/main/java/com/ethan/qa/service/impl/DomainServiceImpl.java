package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.response.ResponseState;
import com.ethan.common.utils.ELog;
import com.ethan.qa.mapper.DomainMapper;
import com.ethan.qa.pojo.po.Domain;
import com.ethan.qa.pojo.vo.DomainO;
import com.ethan.qa.pojo.vo.DomainsO;
import com.ethan.qa.service.IDomainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@Service
public class DomainServiceImpl extends BaseServiceImpl<DomainMapper, Domain> implements IDomainService {

    @Override
    public boolean hasDomain(Long domainId) {
        QueryWrapper<Domain> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_id", domainId);
        return count(wrapper) != 0;
    }

    @Override
    public ResponseResult createDomain(String name) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("创建领域 UID ---> " + uid);

        // 检查管理员权限
        if (!isAdmin().isSuccess()) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        Domain domain = new Domain();
        domain.setDomainName(name);
        if (!save(domain)) {
            return new ResponseResult(ResponseState.DB_FAIL);
        }
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult deleteDomain(Long id) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("删除领域 UID ---> " + uid);

        // 检查管理员权限
        if (!isAdmin().isSuccess()) {
            return new ResponseResult(ResponseState.PERMISSION_DENIED);
        }

        if (!removeById(id)) {
            return new ResponseResult(ResponseState.DB_FAIL);
        }
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult listDomain() {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("获取领域列表 UID ---> " + uid);

        List<Domain> origin = list();
        List<DomainO> domains = new ArrayList<>();
        for (Domain d : origin) {
            domains.add(new DomainO(d));
        }
        return ResponseResult.SUCCESS(new DomainsO(domains));
    }

    @Override
    public String getDomainName(Long domainId) {
        Domain domain = getById(domainId);
        return domain.getDomainName();
    }
}
