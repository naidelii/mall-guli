package com.mall.admin.biz.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.LoginUserDto;
import com.mall.admin.biz.service.*;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.exception.GlobalException;
import com.mall.common.base.utils.PasswordUtils;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthServiceImpl implements IAuthService {
    private final IVerifyCodeService verifyCodeService;
    private final ISysUserService userService;
    private final ISysPermissionService permissionService;
    private final ISysRoleService roleService;

    @Override
    public String authenticateUser(LoginUserDto loginUserDto) {
        verifyCodeService.verifyCode(loginUserDto.getRealKey(), loginUserDto.getCaptcha());
        SysUser sysUser = userService.queryByUserName(loginUserDto.getUsername());
        checkUser(loginUserDto.getUsername(), loginUserDto.getPassword(), sysUser);
        LoginUser loginUser = new LoginUser();
        // 拷贝属性
        BeanUtils.copyProperties(sysUser, loginUser);
        // 查询当前用户拥有的角色
        String userId = sysUser.getId();
        List<SysRole> roleList = roleService.selectRolesByUserId(userId);
        if (CollUtil.isNotEmpty(roleList)) {
            Set<String> roleCodes = roleList.stream()
                    .map(SysRole::getRoleCode)
                    .collect(Collectors.toSet());
            loginUser.setRoles(roleCodes);
            Set<String> perms = permissionService.selectPermsByRoleCodes(roleCodes);
            loginUser.setPermissions(perms);
        }
        // 执行登录方法
        SecurityContext.login(loginUser);
        return StpUtil.getTokenValue();
    }

    public void checkUser(String username, String password, SysUser dbUser) {
        if (dbUser == null) {
            log.error("用户不存在,{}", username);
            throw new GlobalException("账号或密码不正确");
        }
        boolean matches = PasswordUtils.matches(password, dbUser.getSalt(), dbUser.getPassword());
        if (!matches) {
            throw new GlobalException("账号或密码不正确");
        }
        if (CommonConstants.FREEZE == dbUser.getStatus()) {
            throw new GlobalException("账号已被锁定，请联系管理员");
        }
    }

}
