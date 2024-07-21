package com.mall.admin.biz.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author naidelii
 * 角色权限关联表
 */
@Data
@TableName("sys_role_permission")
public class SysRolePermission {
    /**
     * 主键
     */
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单权限ID
     */
    private String permissionId;


}
