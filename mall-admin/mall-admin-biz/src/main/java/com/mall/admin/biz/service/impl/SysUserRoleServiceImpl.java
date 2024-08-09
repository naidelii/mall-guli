package com.mall.admin.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.entity.SysUserRole;
import com.mall.admin.biz.mapper.SysUserRoleMapper;
import com.mall.admin.biz.service.ISysUserRoleService;
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
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void saveOrUpdate(String userId, Set<String> roleIdList) {
        // 先删除用户与角色关系
        baseMapper.deleteByUserId(userId);
        if (CollUtil.isEmpty(roleIdList)) {
            return;
        }
        List<SysUserRole> list = roleIdList.stream()
                .map(roleId -> new SysUserRole(userId, roleId))
                .collect(Collectors.toList());
        saveBatch(list);
    }

    @Override
    public List<SysRole> selectRolesByUserId(String userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    @Override
    public void deleteByUserIds(Set<String> userIds) {
        baseMapper.deleteUserRole(userIds);
    }

    @Override
    public void deleteByRoleIds(Set<String> roleIds) {
        baseMapper.deleteByRoleIds(roleIds);
    }

}
