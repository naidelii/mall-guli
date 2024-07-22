package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.vo.SysRoleListVo;
import com.mall.common.security.domain.LoginUser;

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
}
