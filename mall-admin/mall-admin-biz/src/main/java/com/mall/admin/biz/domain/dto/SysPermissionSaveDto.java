package com.mall.admin.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author naidelii
 */
@Data
public class SysPermissionSaveDto {

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 父级id（0：顶级）
     */
    @NotBlank(message = "上级菜单不能为空")
    private String parentId;

    /**
     * 类型（0：目录，1：菜单，2：按钮权限）
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 菜单权限编码（user:list,user:create）
     */
    private String perms;

}
