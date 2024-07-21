package com.mall.admin.biz.controller;


import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;

/**
 * Controller公共组件
 *
 * @author Naidelii
 */
public abstract class AbstractController {

    protected LoginUser getUser() {
        return SecurityContext.getLoginUser();
    }

    protected String getUserId() {
        return SecurityContext.getLoginUser().getId();
    }
}
