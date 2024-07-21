package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysUser;


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
}
