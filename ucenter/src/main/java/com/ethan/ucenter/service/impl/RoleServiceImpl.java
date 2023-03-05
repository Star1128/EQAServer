package com.ethan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ucenter.mapper.RoleMapper;
import com.ethan.ucenter.pojo.po.Role;
import com.ethan.ucenter.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public boolean addRole(Long appId, int role, Long uid) {
        Role newRole = new Role();
        newRole.setAppId(appId);
        newRole.setRole(role);
        newRole.setUserId(uid);
        return save(newRole);
    }

    @Override
    public boolean checkRole(Long appId, Long uid) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", appId);
        wrapper.eq("user_id", uid);
        return count(wrapper) != 0;
    }
}
