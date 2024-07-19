package com.mall.common.security.context;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.mall.common.security.constant.SecurityConstants;
import com.mall.common.security.entity.LoginUser;

import java.util.Set;

/**
 * @author naidelii
 */
public final class SecurityContext {

    /**
     * 私有化构造函数
     */
    private SecurityContext() {

    }

    /**
     * 设置用户缓存到会话中
     */
    public static void login(LoginUser loginUser) {
        StpUtil.login(loginUser.getId());
        StpUtil.getTokenSession().set(SecurityConstants.USER_KEY, loginUser);
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getLoginUser() {
        // 获取会话信息
        SaSession tokenSession = StpUtil.getTokenSession();
        return (LoginUser) tokenSession.get(SecurityConstants.USER_KEY);
    }

    /**
     * 获取登录人的用户名
     *
     * @return 用户名
     */
    public static String getUserName() {
        // 当前登录用户
        LoginUser loginUser = getLoginUser();
        return loginUser.getUsername();
    }

    public static Set<String> getPermissions() {
        return getLoginUser().getPermissions();
    }

    public static Set<String> getRoles() {
        return getLoginUser().getRoles();
    }
}
