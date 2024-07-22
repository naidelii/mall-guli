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
     * 根据type查询菜单权限
     *
     * @param types 类型
     * @return List<SysPermission>
     */
    List<SysPermission> selectPermissionListByType(@Param("types") Set<Integer> types);
}
