package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author naidelii
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<SysPermission> selectPermissionsByUserId(@Param("userId") String userId);
}
