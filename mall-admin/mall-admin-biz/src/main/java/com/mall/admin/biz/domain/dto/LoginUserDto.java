package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class LoginUserDto {
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    private String username;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空！")
    private String captcha;

    /**
     * 验证码key
     */
    @NotEmpty(message = "验证码key不能为空！")
    private String realKey;

}
