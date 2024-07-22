package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @author Naidelii
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {


    /**
     * 根据角色ID，删除拥有的菜单权限
     *
     * @param roleId 角色id
     */
    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") String roleId);

}
