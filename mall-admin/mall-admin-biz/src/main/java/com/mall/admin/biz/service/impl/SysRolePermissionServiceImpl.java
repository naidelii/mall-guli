package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.mapper.SysRolePermissionMapper;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {
    private final SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermission> listCurrentUserMenus() {
        // 获取当前登录用户
        LoginUser loginUser = SecurityContext.getLoginUser();
        // 如果是管理员，则查询所有
        return loginUser.isAdmin() ?
                permissionMapper.listAllMenus() :
                baseMapper.listMenusByUserId(loginUser.getId());
    }

    @Override
    public List<SysPermission> listMenusByRoleId(String roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }


}
