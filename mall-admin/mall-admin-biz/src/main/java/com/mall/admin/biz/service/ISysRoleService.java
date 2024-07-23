package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.vo.SysRoleInfoVo;
import com.mall.admin.biz.domain.vo.SysRoleListVo;

import java.util.List;
import java.util.Set;


/**
 * 角色
 *
 * @author naidelii
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param query    查询对象
     * @return IPage<SysRoleListVo>
     */
    IPage<SysRoleListVo> selectListPage(Integer pageNo, Integer pageSize, SysRoleListQuery query);

    /**
     * 查询角色列表
     *
     * @return List<SysRoleListVo>
     */
    List<SysRoleListVo> selectList();


    /**
     * 新增角色
     *
     * @param role          角色信息
     * @param permissionIds 菜单权限id
     */
    void saveRole(SysRole role, Set<String> permissionIds);

    /**
     * 根据角色id获取角色详情
     *
     * @param roleId 角色id
     * @return 角色详情
     */
    SysRoleInfoVo selectInfoById(String roleId);

    /**
     * 修改角色
     *
     * @param role          角色信息
     * @param permissionIds 菜单权限id
     */
    void updateRole(SysRole role, Set<String> permissionIds);

    /**
     * 根据角色id集合删除角色
     *
     * @param roleIds 角色id集合
     */
    void deleteByIds(Set<String> roleIds);
}
