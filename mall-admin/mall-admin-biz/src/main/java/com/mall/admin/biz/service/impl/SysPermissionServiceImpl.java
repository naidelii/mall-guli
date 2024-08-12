package com.mall.admin.biz.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.mapper.SysRolePermissionMapper;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.common.base.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    private final SysRolePermissionMapper rolePermissionMapper;

    @Override
    public List<SysPermission> listAllPermissions() {
        return baseMapper.listAllPermissions();
    }

    @Override
    public List<SysPermission> listAllMenus() {
        return baseMapper.listAllMenus();
    }

    @Override
    public void savePermission(SysPermission permission) {
        baseMapper.insert(permission);
    }

    @Override
    public void updatePermission(SysPermission permission) {
        baseMapper.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 判断是否有子菜单或按钮
        List<SysPermission> list = baseMapper.listPermissionByParentId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new GlobalException("请先删除子菜单或按钮");
        }
        // 删除菜单与角色关联
        rolePermissionMapper.deleteByPermissionId(id);
        baseMapper.deleteById(id);
    }

}
