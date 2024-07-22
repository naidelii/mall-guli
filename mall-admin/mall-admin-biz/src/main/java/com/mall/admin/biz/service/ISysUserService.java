package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.vo.SysUserListVo;


/**
 * 系统用户
 *
 * @author naidelii
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名，查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser queryByUserName(String username);

    /**
     * 分页查询用户列表
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param query    查询对象
     * @return IPage<SysUserListVo>
     */
    IPage<SysUserListVo> selectListPage(Integer pageNo, Integer pageSize, SysUserListQuery query);
}
