package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.entity.SysRolePermission;

import java.util.List;


/**
 * 角色与菜单的关联关系
 *
 * @author Naidelii
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 根据角色id查询菜单权限
     *
     * @param roleId 角色id
     * @return List<SysPermission>
     */
    List<SysPermission> listMenusByRoleId(String roleId);

    /**
     * 查询用户拥有的菜单列表，并组装成树形结构
     *
     * @return 菜单列表
     */
    List<SysPermission> listCurrentUserMenus();

}
