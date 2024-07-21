package com.mall.admin.biz.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.LoginUserDto;
import com.mall.admin.biz.service.IAuthService;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.admin.biz.service.IVerifyCodeService;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.constant.enums.PermissionType;
import com.mall.common.base.exception.GlobalException;
import com.mall.common.base.utils.PasswordUtils;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

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

    @Override
    public String authenticateUser(LoginUserDto loginUser) {
        verifyCodeService.verifyCode(loginUser.getRealKey(), loginUser.getCaptcha());
        SysUser sysUser = userService.queryByUserName(loginUser.getUsername());
        checkUser(loginUser.getUsername(), loginUser.getPassword(), sysUser);
        LoginUser user = new LoginUser();
        // 拷贝属性
        BeanUtils.copyProperties(sysUser, user);
        // 查询用户权限（按钮权限）
        Set<String> permissions = permissionService.getPermissionsByTypeAndUserId(PermissionType.BUTTON_PERMISSIONS.getValue(), sysUser.getId());
        user.setPermissions(permissions);
        // 执行登录方法
        SecurityContext.login(user);
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
