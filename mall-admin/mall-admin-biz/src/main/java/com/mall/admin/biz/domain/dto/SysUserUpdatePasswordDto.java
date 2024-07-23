package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class SysUserUpdatePasswordDto {

    /**
     * 原来的密码
     */
    @NotBlank(message = "原密码不能为空")
    private String password;

    /**
     * 修改后的密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
