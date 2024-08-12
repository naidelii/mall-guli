package com.mall.admin.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;

import java.util.List;


/**
 * 菜单权限
 *
 * @author naidelii
 */
public interface ISysPermissionService extends IService<SysPermission> {


    /**
     * 查询所有菜单权限列表
     *
     * @return List<SysPermissionListVo>
     */
    List<SysPermission> listAllPermissions();


    /**
     * 查询所有菜单列表
     *
     * @return List<SysPermissionListVo>
     */
    List<SysPermission> listAllMenus();

    /**
     * 添加菜单权限
     *
     * @param permission 菜单权限信息
     */
    void savePermission(SysPermission permission);

    /**
     * 修改菜单权限
     *
     * @param permission 菜单权限信息
     */
    void updatePermission(SysPermission permission);

    /**
     * 根据id删除菜单权限信息
     *
     * @param id 主键
     */
    void deleteById(String id);
}
