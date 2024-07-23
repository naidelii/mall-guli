package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class SysUserResetPasswordDto {

    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 修改后的密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
