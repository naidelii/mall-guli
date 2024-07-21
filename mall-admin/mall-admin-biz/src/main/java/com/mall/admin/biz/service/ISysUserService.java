package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;


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
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserInfoVo getUserInfo(String userId);


    /**
     * 分页查询用户列表
     *
     * @param page 分页参数
     * @return 用户列表
     */
    IPage<SysUserInfoVo> selectListPage(Page<SysUser> page);
}
