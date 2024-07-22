package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.biz.domain.entity.SysRolePermission;

import java.util.Set;


/**
 * 角色与菜单的关联关系
 *
 * @author Naidelii
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {


    /**
     * 新增或更新角色的菜单权限信息
     *
     * @param roleId        角色id
     * @param permissionIds 菜单权限集合
     */
    void saveOrUpdate(String roleId, Set<String> permissionIds);
}
