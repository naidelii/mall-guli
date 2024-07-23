package com.mall.admin.biz.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionInfoVo;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.common.base.constant.enums.PermissionType;
import com.mall.common.base.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    private final ISysRolePermissionService rolePermissionService;

    @Override
    public List<SysPermissionListVo> selectPermissionList() {
        List<SysPermission> menuList = baseMapper.selectPermissionListByType(null);
        return menuList.stream()
                .map(SysPermissionListVo::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysPermissionListVo> selectMenuList() {
        Set<Integer> types = new HashSet<>();
        types.add(PermissionType.DIRECTORY.getValue());
        types.add(PermissionType.MENU.getValue());
        List<SysPermission> menuList = baseMapper.selectPermissionListByType(types);
        return menuList.stream()
                .map(SysPermissionListVo::new)
                .collect(Collectors.toList());
    }

    @Override
    public void savePermission(SysPermission permission) {
        baseMapper.insert(permission);
    }

    @Override
    public SysPermissionInfoVo getPermissionInfo(String permissionId) {
        SysPermission permission = baseMapper.selectById(permissionId);
        return new SysPermissionInfoVo(permission);
    }

    @Override
    public void updatePermission(SysPermission permission) {
        baseMapper.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 判断是否有子菜单或按钮
        List<SysPermission> list = baseMapper.selectPermissionListByParentId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new GlobalException("请先删除子菜单或按钮");
        }
        // 删除菜单与角色关联
        rolePermissionService.deleteByPermissionId(id);
        baseMapper.deleteById(id);
    }

}
