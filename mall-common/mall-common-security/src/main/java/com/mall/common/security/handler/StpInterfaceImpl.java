package com.mall.common.security.handler;

import cn.dev33.satoken.stp.StpInterface;
import com.mall.common.security.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author naidelii
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>(SecurityContext.getPermissions());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<>(SecurityContext.getRoles());
    }

}
