package com.mall.admin.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.biz.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author Naidelii
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id，删除关联的角色信息
     *
     * @param userId 用户id
     */
    @Delete("delete from sys_user_role where user_id = #{userId}")
    void deleteByUserId(@Param("userId") String userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param userIds 需要删除的用户ID
     */
    void deleteUserRole(@Param("userIds") Set<String> userIds);
}
