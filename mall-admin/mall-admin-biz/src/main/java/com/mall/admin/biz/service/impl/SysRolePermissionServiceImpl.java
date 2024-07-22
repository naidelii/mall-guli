package com.mall.admin.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import com.mall.admin.biz.mapper.SysRolePermissionMapper;
import com.mall.admin.biz.service.ISysRolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {


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
}
