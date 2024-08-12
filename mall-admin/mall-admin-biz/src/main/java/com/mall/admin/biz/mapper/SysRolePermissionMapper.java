package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.entity.SysRolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据角色id查询菜单权限
     *
     * @param roleId 角色id
     * @return List<SysPermission>
     */
    List<SysPermission> listMenusByRoleId(@Param("roleId") String roleId);

    /**
     * 查询用户拥有的的菜单列表
     *
     * @param userId 用户id
     * @return List<SysPermission>
     */
    List<SysPermission> listMenusByUserId(@Param("userId") String userId);

    /**
     * 删除角色与菜单权限的关联
     *
     * @param roleIds 角色id集合
     * @return 条数
     */
    int deleteRolePermission(@Param("roleIds") Set<String> roleIds);

    /**
     * 根据菜单权限id删除与角色的关联关系
     *
     * @param permissionId 菜单权限id
     * @return 影响的数量
     */
    @Delete("delete from sys_role_permission where permission_id = #{permissionId}")
    int deleteByPermissionId(@Param("permissionId") String permissionId);

    /**
     * 批量新增角色与菜单的关联关系
     *
     * @param list 角色与菜单的关联关系
     * @return 影响的数量
     */
    int saveBatch(@Param("list") List<SysRolePermission> list);
}
