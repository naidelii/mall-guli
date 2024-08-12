package com.mall.auth.biz.domain.entity;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class LoginUserInfo {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * md5密码盐
     */
    private String salt;

    /**
     * 状态（1：正常，0：禁用）
     */
    private Integer status;

    /**
     * 逻辑删除（1：删除，0：未删除）
     */
    private Integer isDeleted;

}
