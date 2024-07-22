package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.common.security.domain.LoginUser;

import java.util.List;
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

    /**
     * 获取菜单数据权限
     *
     * @param loginUser 用户信息
     * @return 菜单权限信息
     */
    Set<String> selectPermsByLoginUser(LoginUser loginUser);

    /**
     * 根据角色id查询菜单权限
     *
     * @param roleId 角色id
     * @return List<SysPermission>
     */
    List<SysPermission> selectPermissionByRoleId(String roleId);

    /**
     * 查询用户拥有的菜单列表，并组装成树形结构
     *
     * @return 菜单列表
     */
    List<SysPermissionTreeVo> selectUserMenuList();
}
