package com.mall.admin.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;

import java.util.List;
import java.util.Set;


/**
 * 菜单权限
 *
 * @author naidelii
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 根据角色CODE集合，查询拥有的权限
     *
     * @param roleCodes 角色CODE集合
     * @return 权限列表
     */
    Set<String> selectPermsByRoleCodes(Set<String> roleCodes);

    /**
     * 查询用户拥有的菜单列表，并组装成树形结构
     *
     * @return 菜单列表
     */
    List<SysPermissionTreeVo> selectUserMenuList();

    /**
     * 查询所有菜单列表
     *
     * @return List<SysPermissionListVo>
     */
    List<SysPermissionListVo> selectAllMenuList();

}
