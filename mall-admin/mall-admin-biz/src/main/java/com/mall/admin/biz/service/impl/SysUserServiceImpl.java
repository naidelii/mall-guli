package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.domain.vo.SysUserListVo;
import com.mall.admin.biz.mapper.SysUserMapper;
import com.mall.admin.biz.service.ISysUserRoleService;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.exception.GlobalException;
import com.mall.common.base.utils.PasswordUtils;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysUserRoleService userRoleService;

    @Override
    public SysUser queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    public IPage<SysUserListVo> selectListPage(Integer pageNo, Integer pageSize, SysUserListQuery query) {
        IPage<SysUser> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername());
        IPage<SysUser> pageList = baseMapper.selectPage(page, queryWrapper);
        List<SysUserListVo> userListVos = pageList.getRecords()
                .stream()
                .map(SysUserListVo::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser user, Set<String> roleIdList) {
        String salt = PasswordUtils.randomGen(8);
        user.setSalt(salt);
        String password = PasswordUtils.encode(user.getPassword(), salt);
        user.setPassword(password);
        baseMapper.insert(user);
        userRoleService.saveOrUpdate(user.getId(), roleIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser sysUser, Set<String> roleIdList) {
        baseMapper.updateById(sysUser);
        userRoleService.saveOrUpdate(sysUser.getId(), roleIdList);
    }

    @Override
    public void deleteUserByIds(Set<String> userIds) {
        // 删除用户与角色关联
        userRoleService.deleteByUserIds(userIds);
        baseMapper.deleteBatchIds(userIds);
    }

    @Override
    public SysUserInfoVo selectInfoById(String userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        SysUserInfoVo vo = new SysUserInfoVo(sysUser);
        List<SysRole> rolelist = userRoleService.selectRolesByUserId(userId);
        Set<String> roleIds = rolelist.stream()
                .map(SysRole::getId)
                .collect(Collectors.toSet());
        vo.setRoleIds(roleIds);
        return vo;
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