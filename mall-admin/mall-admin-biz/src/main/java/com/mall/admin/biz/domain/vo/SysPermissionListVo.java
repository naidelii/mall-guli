package com.mall.admin.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.admin.api.entity.SysPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单权限管理
 *
 * @author naidelii
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysPermissionListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id（0：顶级）
     */
    private String parentId;

    /**
     * 类型（0：目录，1：菜单，2：按钮权限）
     */
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

    public SysPermissionListVo(SysPermission entity) {
        BeanUtil.copyProperties(entity, this);
    }
}
