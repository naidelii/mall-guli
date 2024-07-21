package com.mall.common.security.domain;

import com.mall.common.base.constant.CommonConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
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
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态（1：正常，0：禁用）
     */
    private Integer status;

    /**
     * 性别(0：未知，1：男，2：女)
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 出生日期
     */
    private LocalDateTime birthdate;

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
