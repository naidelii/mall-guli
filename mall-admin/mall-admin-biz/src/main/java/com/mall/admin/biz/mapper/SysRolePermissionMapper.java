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
    List<SysPermission> selectPermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 查询用户的菜单列表或菜单权限
     *
     * @param userId 用户id
     * @param types  类型
     * @return List<SysPermission>
     */
    List<SysPermission> selectPermissionsByUserIdAndType(@Param("userId") String userId, @Param("types") Set<Integer> types);

    /**
     * 删除角色与菜单权限的关联
     *
     * @param roleIds 角色id集合
     * @return 条数
     */
    int deleteRolePermission(@Param("roleIds") Set<String> roleIds);
}
