package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.utils.ELog;
import com.ethan.qa.mapper.UserDomainMapper;
import com.ethan.qa.pojo.po.UserDomain;
import com.ethan.qa.service.IDomainService;
import com.ethan.qa.service.IUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/24
 */
@Service
public class UserDomainServiceImpl extends BaseServiceImpl<UserDomainMapper, UserDomain> implements IUserDomainService {

    @Autowired
    private IDomainService mDomainService;

    @Override
    public ResponseResult getInfo() {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("获取领域 UID ---> " + uid);

        QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        return ResponseResult.SUCCESS(list(wrapper));
    }

    @Override
    public long userDomainAmount(long uid) {
        QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        return count(wrapper);
    }

    @Override
    public ResponseResult joinDomain(Long domainId) {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData()); // 约定大于配置，UID 一定是 Long 型：）
        ELog.INFO("加入领域 UID ---> " + uid);

        // 判重
        if (!mDomainService.hasDomain(domainId)) {
            return ResponseResult.FAIL("该领域不存在");
        }

        // 判重
        QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("domain_id", domainId);
        if (count(wrapper) != 0) {
            return ResponseResult.FAIL("用户已加入该领域");
        }

        // 插入数据库
        UserDomain userDomain = new UserDomain();
        userDomain.setUserId(uid);
        userDomain.setDomainId(domainId);
        save(userDomain);
        return ResponseResult.SUCCESS();
    }
}
