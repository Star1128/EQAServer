package com.ethan.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ucenter.pojo.po.Role;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ethan
 * @since 2023/02/11
 */
public interface IRoleService extends IService<Role> {

    /**
     * 添加角色
     */
    boolean addRole(Long appId, int role, Long uid);

    /**
     * 检查 UID 是否具有对应 APP 的权限
     */
    boolean checkRole(Long appId, Long uid);
}
