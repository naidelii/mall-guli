package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.entity.SysUserRole;

import java.util.List;


/**
 * 用户与角色的关联关系
 *
 * @author Naidelii
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户id获取角色数据权限
     *
     * @param userId 用户id
     * @return 角色信息
     */
    List<SysRole> listRolesByUserId(String userId);

}
