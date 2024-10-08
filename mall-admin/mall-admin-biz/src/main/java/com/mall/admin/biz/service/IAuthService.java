package com.mall.admin.biz.service;


import com.mall.admin.biz.domain.dto.LoginUserDto;

/**
 * @author naidelii
 */
public interface IAuthService {

    /**
     * 登录认证
     *
     * @param loginUser 用户信息
     * @return token
     */
    String authenticateUser(LoginUserDto loginUser);
}
