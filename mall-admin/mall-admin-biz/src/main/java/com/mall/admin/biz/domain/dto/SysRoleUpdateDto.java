package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author naidelii
 */
@Data
public class SysRoleUpdateDto {

    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
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
