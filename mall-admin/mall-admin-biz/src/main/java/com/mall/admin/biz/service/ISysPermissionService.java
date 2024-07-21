package com.mall.admin.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;

import java.util.Set;


/**
 * 菜单权限
 *
 * @author naidelii
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 根据用户ID以及类型 查询权限
     *
     * @param type   权限类型
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> getPermissionsByTypeAndUserId(int type, String userId);
}
