package com.ethan.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ethan.common.response.ResponseResult;
import com.ethan.common.utils.ELog;
import com.ethan.qa.mapper.UserDomainMapper;
import com.ethan.qa.pojo.po.UserDomain;
import com.ethan.qa.pojo.vo.UserDomainO;
import com.ethan.qa.pojo.vo.UserDomainsO;
import com.ethan.qa.service.IDomainService;
import com.ethan.qa.service.IUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserDomainServiceImpl extends BaseServiceImpl<UserDomainMapper, UserDomain> implements IUserDomainService {

    @Autowired
    private IDomainService mDomainService;

    @Override
    public ResponseResult getInfo() {
        ResponseResult result;
        if (!(result = T2U()).isSuccess()) {
            return result;
        }
        long uid = Long.parseLong((String) result.getData());
        ELog.INFO("获取领域 UID ---> " + uid);

        QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        List<UserDomainO> domains = new ArrayList<>();
        for (UserDomain userDomain : list(wrapper)) {
            String domainName = mDomainService.getDomainName(userDomain.getDomainId());
            domains.add(new UserDomainO(userDomain, domainName));
        }

        return ResponseResult.SUCCESS(new UserDomainsO(domains));
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
        long uid = Long.parseLong((String) result.getData());
        ELog.INFO("加入领域 UID ---> " + uid);

        // 判空
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
        userDomain.setExp(0);
        save(userDomain);
        return ResponseResult.SUCCESS();
    }

    @Override
    public void joinDomain(Long uid, Long domainId) {
        // 判重
        QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("domain_id", domainId);
        if (count(wrapper) != 0) {
            return;
        }

        // 插入数据库
        UserDomain userDomain = new UserDomain();
        userDomain.setUserId(uid);
        userDomain.setDomainId(domainId);
        userDomain.setExp(0);
        save(userDomain);
    }

    @Override
    public void updateExp(long uid, long did, int exp) {
        UpdateWrapper<UserDomain> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("domain_id", did);
        UserDomain userDomain = getOne(wrapper);
        userDomain.setExp(userDomain.getExp() + exp);
        updateById(userDomain);
    }

    @Override
    public int getExp(Long uid, long did) {
        UpdateWrapper<UserDomain> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("domain_id", did);
        UserDomain userDomain = getOne(wrapper);
        return userDomain.getExp();
    }
}
