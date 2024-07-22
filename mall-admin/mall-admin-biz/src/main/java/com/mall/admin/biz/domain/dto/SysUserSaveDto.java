package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author naidelii
 */
@Data
public class SysUserSaveDto {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 角色ID列表
     */
    private Set<String> roleIds;

}
