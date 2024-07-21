package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author naidelii
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 查询用户拥有的菜单列表
     *
     * @param codes 角色CODE集合
     * @return 菜单列表
     */
    List<SysPermission> selectUserMenuListByRoleCodes(@Param("codes") Set<String> codes);

    /**
     * 根据角色CODE集合，查询拥有的权限
     *
     * @param codes 角色CODE集合
     * @return 权限列表
     */
    Set<String> selectPermsByRoleCodes(@Param("codes") Set<String> codes);

    /**
     * 查询所有菜单列表
     *
     * @return 菜单列表
     */
    List<SysPermission> selectAllMenuList();
}
