package com.mall.common.security.domain;

import com.mall.common.base.constant.CommonConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author naidelii
 */
@Data
public class LoginUser {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色标识集合
     */
    private Set<String> roles;

    /**
     * 权限标识集合
     */
    private Set<String> permissions;

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    private boolean isAdmin(String id) {
        return StringUtils.isNotBlank(id) && CommonConstants.SUPER_ADMIN.equals(id);
    }

}
