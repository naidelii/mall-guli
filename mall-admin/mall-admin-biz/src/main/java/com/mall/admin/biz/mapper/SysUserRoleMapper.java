package com.mall.admin.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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
     * @return 操作条数
     */
    int deleteUserRole(@Param("userIds") Set<String> userIds);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(@Param("userId") String userId);

    /**
     * 删除角色与用户的关联
     *
     * @param roleIds 需要删除的角色id
     * @return 操作条数
     */
    int deleteByRoleIds(@Param("roleIds") Set<String> roleIds);
}
