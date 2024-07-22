package com.mall.admin.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.admin.api.entity.SysRole;
import lombok.Data;

import java.io.Serializable;

/**
 * @author naidelii
 */
@Data
public class SysRoleListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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


    public SysRoleListVo(SysRole sysRole) {
        BeanUtil.copyProperties(sysRole, this);
    }
}
