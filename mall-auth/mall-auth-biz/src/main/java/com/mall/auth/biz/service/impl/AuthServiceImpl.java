package com.mall.auth.biz.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mall.auth.biz.domain.dto.LoginUserDto;
import com.mall.auth.biz.domain.entity.LoginUserInfo;
import com.mall.auth.biz.mapper.LoginUserMapper;
import com.mall.auth.biz.service.IAuthService;
import com.mall.auth.biz.service.IVerifyCodeService;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.exception.GlobalException;
import com.mall.common.base.utils.PasswordUtils;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthServiceImpl implements IAuthService {
    private final IVerifyCodeService verifyCodeService;
    private final LoginUserMapper loginUserMapper;


    @Override
    public String authenticateUser(LoginUserDto loginUserDto) {
        verifyCodeService.verifyCode(loginUserDto.getRealKey(), loginUserDto.getCaptcha());
        LoginUserInfo loginUserInfo = loginUserMapper.queryByUserName(loginUserDto.getUsername());
        validateUser(loginUserDto, loginUserInfo);
        // 登录用户信息
        LoginUser loginUser = new LoginUser();
        // 拷贝属性
        BeanUtils.copyProperties(loginUserInfo, loginUser);
        Set<String> roleCodes = selectRoleByUserId(loginUser.getId());
        loginUser.setRoles(roleCodes);
        Set<String> perms = selectPermsByUserId(loginUser.getId());
        loginUser.setPermissions(perms);
        // 执行登录方法
        SecurityContext.login(loginUser);
        return StpUtil.getTokenValue();
    }

    private Set<String> selectPermsByUserId(String userId) {
        if (LoginUser.isAdmin(userId)) {
            List<String> perms = loginUserMapper.listAllPermission();
            return new HashSet<>(perms);
        }
        List<String> perms = loginUserMapper.listPermsByUserId(userId);
        return new HashSet<>(perms);
    }

    private Set<String> selectRoleByUserId(String userId) {
        if (LoginUser.isAdmin(userId)) {
            return Collections.singleton(CommonConstants.SUPER_ADMIN_ROLE);
        }
        List<String> roles = loginUserMapper.selectRolesByUserId(userId);
        return new HashSet<>(roles);
    }


    private void validateUser(LoginUserDto loginUserDto, LoginUserInfo sysUser) {
        if (sysUser == null) {
            log.error("用户不存在: {}", loginUserDto.getUsername());
            throw new GlobalException("账号或密码不正确");
        }
        if (!PasswordUtils.matches(loginUserDto.getPassword(), sysUser.getSalt(), sysUser.getPassword())) {
            log.error("密码不正确: {}", loginUserDto.getUsername());
            throw new GlobalException("账号或密码不正确");
        }
        if (CommonConstants.FREEZE == sysUser.getStatus()) {
            log.error("用户已冻结: {}", loginUserDto.getUsername());
            throw new GlobalException("账号已被锁定，请联系管理员");
        }
    }

}
