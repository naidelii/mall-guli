package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.biz.domain.entity.SysUserRole;

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
     * 批量删除用户和角色关联
     *
     * @param userIds 需要删除的用户ID
     */
    void deleteUserRole(Set<String> userIds);
}
