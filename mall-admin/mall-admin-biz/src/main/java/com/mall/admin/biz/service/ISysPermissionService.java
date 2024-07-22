package com.mall.admin.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.common.security.domain.LoginUser;

import java.util.List;
import java.util.Set;


/**
 * 菜单权限
 *
 * @author naidelii
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 查询用户拥有的菜单列表，并组装成树形结构
     *
     * @return 菜单列表
     */
    List<SysPermissionTreeVo> selectUserMenuList();

    /**
     * 查询所有菜单权限列表
     *
     * @return List<SysPermissionListVo>
     */
    List<SysPermissionListVo> selectPermissionList();

    /**
     * 获取菜单数据权限
     *
     * @param loginUser 用户信息
     * @return 菜单权限信息
     */
    Set<String> selectPermsByLoginUser(LoginUser loginUser);
}
