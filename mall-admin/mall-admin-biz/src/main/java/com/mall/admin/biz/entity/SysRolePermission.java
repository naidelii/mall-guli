package com.mall.admin.biz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author naidelii
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
