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
     * 根据用户id查询拥有的菜单列表
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysPermission> listMenusByUserId(String userId);

}
