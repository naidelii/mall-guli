package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.vo.SysRoleListVo;
import com.mall.common.security.domain.LoginUser;

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
     * 获取角色数据权限
     *
     * @param loginUser 登录用户信息
     * @return 角色权限信息
     */
    Set<String> selectRoleByLoginUser(LoginUser loginUser);

    /**
     * 根据用户id获取角色数据权限
     *
     * @param userId 用户id
     * @return 角色信息
     */
    List<SysRole> selectRolesByUserId(String userId);

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
}
