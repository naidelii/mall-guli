package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.vo.SysRoleInfoVo;
import com.mall.admin.biz.domain.vo.SysRoleListVo;
import com.mall.admin.biz.mapper.SysRoleMapper;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.admin.biz.service.ISysRoleService;
import com.mall.admin.biz.service.ISysUserRoleService;
import com.mall.common.data.utils.PageUtils;
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

    private final ISysRolePermissionService rolePermissionService;
    private final ISysUserRoleService userRoleService;

    @Override
    public IPage<SysRoleListVo> selectListPage(Integer pageNo, Integer pageSize, SysRoleListQuery query) {
        IPage<SysRole> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getRoleName()), SysRole::getRoleName, query.getRoleName());
        IPage<SysRole> pageList = baseMapper.selectPage(page, queryWrapper);
        List<SysRoleListVo> userListVos = pageList.getRecords()
                .stream()
                .map(SysRoleListVo::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

    @Override
    public List<SysRoleListVo> selectList() {
        List<SysRole> list = baseMapper.selectList(null);
        return list.stream()
                .map(SysRoleListVo::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole role, Set<String> permissionIds) {
        baseMapper.insert(role);
        // 保存角色与菜单关系
        rolePermissionService.saveOrUpdate(role.getId(), permissionIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRole role, Set<String> permissionIds) {
        baseMapper.updateById(role);
        // 保存角色与菜单关系
        rolePermissionService.saveOrUpdate(role.getId(), permissionIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<String> roleIds) {
        // 删除用户与角色的关联
        userRoleService.deleteByRoleIds(roleIds);
        // 删除角色与菜单权限的关联
        rolePermissionService.deleteRolePermission(roleIds);
        // 删除角色
        baseMapper.deleteBatchIds(roleIds);
    }

    @Override
    public SysRoleInfoVo selectInfoById(String roleId) {
        SysRole sysRole = baseMapper.selectById(roleId);
        SysRoleInfoVo vo = new SysRoleInfoVo(sysRole);
        List<SysPermission> permissionList = rolePermissionService.selectPermissionByRoleId(roleId);
        Set<String> permissionIds = permissionList.stream()
                .map(SysPermission::getId)
                .collect(Collectors.toSet());
        vo.setPermissionIds(permissionIds);
        return vo;
    }

}
