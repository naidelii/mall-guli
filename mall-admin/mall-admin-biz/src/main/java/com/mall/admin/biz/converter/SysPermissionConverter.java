package com.mall.admin.biz.converter;

import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.common.base.constant.CommonConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author naidelii
 */
public final class SysPermissionConverter {

    private SysPermissionConverter() {

    }

    public static List<SysPermissionListVo> buildMenuList(List<SysPermission> menuList) {
        return menuList.stream()
                .map(SysPermissionListVo::new)
                .collect(Collectors.toList());
    }

    public static List<SysPermissionTreeVo> buildMenuTree(List<SysPermission> list) {
        Map<String, List<SysPermission>> permissionMap = list.stream()
                .collect(Collectors.groupingBy(SysPermission::getParentId));
        return doBuildMenuTree(CommonConstants.PARENT_CODE, permissionMap);
    }

    private static List<SysPermissionTreeVo> doBuildMenuTree(String parentId, Map<String, List<SysPermission>> permissionMap) {
        return permissionMap.getOrDefault(parentId, Collections.emptyList())
                .stream()
                .map(entity -> {
                    SysPermissionTreeVo vo = new SysPermissionTreeVo(entity);
                    vo.setChildren(doBuildMenuTree(entity.getId(), permissionMap));
                    return vo;
                })
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }
}
