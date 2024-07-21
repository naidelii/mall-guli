package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysRole;

import java.util.List;


/**
 * 角色
 *
 * @author naidelii
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(String userId);

}
