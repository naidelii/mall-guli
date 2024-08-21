package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author naidelii
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据type查询菜单权限
     *
     * @return List<SysPermission>
     */
    List<SysPermission> listAllMenus();

    /**
     * 根据type查询菜单权限
     *
     * @return List<SysPermission>
     */
    List<SysPermission> listAllPermissions();

    /**
     * 根据type查询菜单权限
     *
     * @param types 类型
     * @return List<SysPermission>
     */
    List<SysPermission> selectPermissionListByType(@Param("types") Set<Integer> types);

    /**
     * 根据parentId查询子级菜单
     *
     * @param parentId 父级id
     * @return List<SysPermission>
     */
    List<SysPermission> listPermissionByParentId(@Param("parentId") String parentId);

    /**
     * 根据id查询详细信息
     *
     * @param id 主键
     * @return SysPermissionInfoVo
     */
    SysPermissionInfoVo getDetailsById(@Param("id") String id);
}
