package com.mall.admin.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.entity.SysUserRole;
import com.mall.admin.biz.mapper.SysUserMapper;
import com.mall.admin.biz.mapper.SysUserRoleMapper;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.exception.GlobalException;
import com.mall.common.base.utils.PasswordUtils;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserRoleMapper userRoleMapper;

    @Override
    public IPage<SysUser> listUsersByPage(Integer pageNo, Integer pageSize, SysUserListQuery query) {
        IPage<SysUser> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser user, Set<String> roleIdList) {
        String salt = PasswordUtils.randomGen(8);
        String password = PasswordUtils.encode(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        // 保存用户信息
        baseMapper.insert(user);
        // 保存用户角色关系
        saveOrUpdateUserRoles(user.getId(), roleIdList);
    }

    private void saveOrUpdateUserRoles(String userId, Set<String> roleIdList) {
        // 删除用户现有的角色关系
        userRoleMapper.deleteByUserId(userId);
        // 添加新的用户角色关系
        if (CollUtil.isNotEmpty(roleIdList)) {
            List<SysUserRole> roleList = roleIdList.stream()
                    .map(roleId -> new SysUserRole(userId, roleId))
                    .collect(Collectors.toList());
            userRoleMapper.saveBatch(roleList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser sysUser, Set<String> roleIdList) {
        // 更新用户信息
        baseMapper.updateById(sysUser);
        // 更新用户角色关系
        saveOrUpdateUserRoles(sysUser.getId(), roleIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserByIds(Set<String> userIds) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        baseMapper.deleteBatchIds(userIds);
    }

    @Override
    public void updatePassword(String userId, String password, String newPassword) {
        SysUser sysUser = baseMapper.selectById(userId);
        boolean matches = PasswordUtils.matches(password, sysUser.getSalt(), sysUser.getPassword());
        if (!matches) {
            throw new GlobalException("原密码不正确");
        }
        String newSalt = PasswordUtils.randomGen(8);
        sysUser.setSalt(newSalt);
        sysUser.setPassword(PasswordUtils.encode(newPassword, newSalt));
        baseMapper.updateById(sysUser);
    }

    @Override
    public void resetPassword(String userId, String newPassword) {
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        String newSalt = PasswordUtils.randomGen(8);
        updateUser.setSalt(newSalt);
        updateUser.setPassword(PasswordUtils.encode(newPassword, newSalt));
        baseMapper.updateById(updateUser);
    }


}