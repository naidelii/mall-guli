package com.mall.admin.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionVo;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public Set<String> selectPermsByRoleCodes(Set<String> roleCodes) {
        return baseMapper.selectPermsByRoleCodes(roleCodes);
    }

    @Override
    public List<SysPermissionVo> selectUserMenuList() {
        LoginUser loginUser = SecurityContext.getLoginUser();
        Set<String> roles = loginUser.getRoles();
        boolean isAdmin = loginUser.isAdmin();
        if (!isAdmin && roles.isEmpty()) {
            return Collections.emptyList();
        }
        List<SysPermission> sysPermissions = isAdmin ? baseMapper.selectAllMenuList() : baseMapper.selectUserMenuListByRoleCodes(roles);
        return buildMenuTree(sysPermissions);
    }

    private List<SysPermissionVo> buildMenuTree(List<SysPermission> list) {
        Map<String, List<SysPermission>> permissionMap = list.stream()
                .collect(Collectors.groupingBy(SysPermission::getParentId));
        return buildTree(CommonConstants.PARENT_CODE, permissionMap);
    }

    private List<SysPermissionVo> buildTree(String parentId, Map<String, List<SysPermission>> permissionMap) {
        return permissionMap.getOrDefault(parentId, Collections.emptyList())
                .stream()
                .map(entity -> {
                    SysPermissionVo vo = new SysPermissionVo(entity);
                    vo.setChildren(buildTree(entity.getId(), permissionMap));
                    return vo;
                })
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }
}
