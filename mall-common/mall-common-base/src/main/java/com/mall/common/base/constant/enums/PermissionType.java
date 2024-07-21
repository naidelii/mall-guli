package com.mall.common.base.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 * 菜单权限类型
 */
@Getter
@AllArgsConstructor
public enum PermissionType {
    /**
     * 目录
     */
    DIRECTORY(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮权限
     */
    BUTTON_PERMISSIONS(2);

    private final int value;

}