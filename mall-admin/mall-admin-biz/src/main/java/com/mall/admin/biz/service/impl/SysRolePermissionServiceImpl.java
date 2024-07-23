package com.mall.admin.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.mapper.SysRolePermissionMapper;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.constant.enums.PermissionType;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {
    private final SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermissionTreeVo> selectUserMenuList() {
        LoginUser loginUser = SecurityContext.getLoginUser();
        Set<Integer> types = new HashSet<>();
        types.add(PermissionType.DIRECTORY.getValue());
        types.add(PermissionType.MENU.getValue());
        List<SysPermission> sysPermissions = getUserPermissionsByTypes(loginUser, types);
        return buildMenuTree(sysPermissions);
    }

    @Override
    public void deleteRolePermission(Set<String> roleIds) {
        baseMapper.deleteRolePermission(roleIds);
    }

    private List<SysPermissionTreeVo> buildMenuTree(List<SysPermission> list) {
        Map<String, List<SysPermission>> permissionMap = list.stream()
                .collect(Collectors.groupingBy(SysPermission::getParentId));
        return buildTree(CommonConstants.PARENT_CODE, permissionMap);
    }

    private List<SysPermissionTreeVo> buildTree(String parentId, Map<String, List<SysPermission>> permissionMap) {
        return permissionMap.getOrDefault(parentId, Collections.emptyList())
                .stream()
                .map(entity -> {
                    SysPermissionTreeVo vo = new SysPermissionTreeVo(entity);
                    vo.setChildren(buildTree(entity.getId(), permissionMap));
                    return vo;
                })
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdate(String roleId, Set<String> permissionIds) {
        // 先删除用户与角色关系
        baseMapper.deleteByRoleId(roleId);
        if (CollUtil.isEmpty(permissionIds)) {
            return;
        }
        List<SysRolePermission> list = permissionIds.stream()
                .map(permissionId -> new SysRolePermission(roleId, permissionId))
                .collect(Collectors.toList());
        saveBatch(list);
    }

    @Override
    public Set<String> selectPermsByLoginUser(LoginUser loginUser) {
        Set<Integer> types = new HashSet<>();
        types.add(PermissionType.BUTTON_PERMISSIONS.getValue());
        List<SysPermission> permissionList = getUserPermissionsByTypes(loginUser, types);
        return permissionList.stream()
                .map(SysPermission::getPerms)
                .collect(Collectors.toSet());
    }


    @Override
    public List<SysPermission> selectPermissionByRoleId(String roleId) {
        return baseMapper.selectPermissionByRoleId(roleId);
    }


    private List<SysPermission> getUserPermissionsByTypes(LoginUser loginUser, Set<Integer> types) {
        return loginUser.isAdmin() ?
                permissionMapper.selectPermissionListByType(types) :
                baseMapper.selectPermissionsByUserIdAndType(loginUser.getId(), types);
    }

}
