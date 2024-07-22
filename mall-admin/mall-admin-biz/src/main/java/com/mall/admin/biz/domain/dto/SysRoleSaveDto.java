package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author naidelii
 */
@Data
public class SysRoleSaveDto {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 菜单权限列表
     */
    private Set<String> permissionIds;

}
