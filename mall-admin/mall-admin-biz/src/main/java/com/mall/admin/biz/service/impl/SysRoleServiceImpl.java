package com.mall.admin.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import com.mall.admin.biz.mapper.SysRoleMapper;
import com.mall.admin.biz.mapper.SysRolePermissionMapper;
import com.mall.admin.biz.mapper.SysUserRoleMapper;
import com.mall.admin.biz.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRolePermissionMapper rolePermissionMapper;
    private final SysUserRoleMapper userRoleMapper;

    @Override
    public IPage<SysRole> listRolesByPage(Integer pageNo, Integer pageSize, SysRoleListQuery query) {
        IPage<SysRole> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getRoleName()), SysRole::getRoleName, query.getRoleName());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole role, Set<String> permissionIds) {
        baseMapper.insert(role);
        // 保存角色与菜单关系
        saveOrUpdateRolePermissions(role.getId(), permissionIds);
    }


    private void saveOrUpdateRolePermissions(String roleId, Set<String> permissionIds) {
        // 删除该角色关联的菜单
        rolePermissionMapper.deleteByRoleId(roleId);
        // 添加角色与菜单新的关联关系
        if (CollUtil.isNotEmpty(permissionIds)) {
            List<SysRolePermission> list = permissionIds.stream()
                    .map(permissionId -> new SysRolePermission(roleId, permissionId))
                    .collect(Collectors.toList());
            rolePermissionMapper.saveBatch(list);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRole role, Set<String> permissionIds) {
        baseMapper.updateById(role);
        // 保存角色与菜单关系
        saveOrUpdateRolePermissions(role.getId(), permissionIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<String> roleIds) {
        // 删除用户与角色的关联
        userRoleMapper.deleteByRoleIds(roleIds);
        // 删除角色与菜单权限的关联
        rolePermissionMapper.deleteRolePermission(roleIds);
        // 删除角色
        baseMapper.deleteBatchIds(roleIds);
    }

}
