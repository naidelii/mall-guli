package com.mall.admin.biz.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * @author naidelii
 * 用户角色关联表
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;


    public SysUserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
