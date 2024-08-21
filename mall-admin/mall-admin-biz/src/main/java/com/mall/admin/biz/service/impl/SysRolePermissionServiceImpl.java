package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.mapper.SysRolePermissionMapper;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.common.base.constant.CacheConstants;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = CacheConstants.USER_MENU_LIST_CACHE_PREFIX + "list", key = "#userId", unless = "#result == null || #result.isEmpty()")
    public List<SysPermission> listMenusByUserId(String userId) {
        // 如果是管理员，则查询所有
        return LoginUser.isAdmin(userId) ?
                permissionMapper.listAllMenus() :
                baseMapper.listMenusByUserId(userId);
    }

    @Override
    public List<SysPermission> listMenusByRoleId(String roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }


}
