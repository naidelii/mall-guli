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
     * 查询用户的菜单列表或菜单权限
     *
     * @param userId 用户id
     * @param types  类型
     * @return List<SysPermission>
     */
    List<SysPermission> selectPermissionsByUserIdAndType(@Param("userId") String userId, @Param("types") Set<Integer> types);

    /**
     * 根据type查询菜单权限
     *
     * @param types 类型
     * @return List<SysPermission>
     */
    List<SysPermission> selectPermissionListByType(@Param("types") Set<Integer> types);

}
