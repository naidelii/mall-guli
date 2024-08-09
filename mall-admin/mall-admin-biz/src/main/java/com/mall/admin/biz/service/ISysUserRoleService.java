package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.entity.SysUserRole;

import java.util.List;
import java.util.Set;


/**
 * 用户与角色的关联关系
 *
 * @author Naidelii
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增或更新用户的角色信息
     *
     * @param userId     用户id
     * @param roleIdList 角色id集合
     */
    void saveOrUpdate(String userId, Set<String> roleIdList);

    /**
     * 根据用户id获取角色数据权限
     *
     * @param userId 用户id
     * @return 角色信息
     */
    List<SysRole> selectRolesByUserId(String userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param userIds 需要删除的用户ID
     */
    void deleteByUserIds(Set<String> userIds);


    /**
     * 批量删除角色和用户关联
     *
     * @param roleIds 需要删除的角色id
     */
    void deleteByRoleIds(Set<String> roleIds);
}
