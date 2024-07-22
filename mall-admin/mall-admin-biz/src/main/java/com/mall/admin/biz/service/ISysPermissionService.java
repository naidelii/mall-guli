package com.mall.admin.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;

import java.util.List;


/**
 * 菜单权限
 *
 * @author naidelii
 */
public interface ISysPermissionService extends IService<SysPermission> {


    /**
     * 查询所有菜单权限列表
     *
     * @return List<SysPermissionListVo>
     */
    List<SysPermissionListVo> selectPermissionList();


}
