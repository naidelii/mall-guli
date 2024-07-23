package com.mall.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.domain.vo.SysUserListVo;

import java.util.Set;


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

    /**
     * 添加用户
     *
     * @param sysUser    用户信息
     * @param roleIdList 角色信息
     */
    void saveUser(SysUser sysUser, Set<String> roleIdList);

    /**
     * 更新
     *
     * @param sysUser    用户信息
     * @param roleIdList 角色信息
     */
    void updateUser(SysUser sysUser, Set<String> roleIdList);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     */
    void deleteUserByIds(Set<String> userIds);

    /**
     * 根据用户id查询用户详情
     *
     * @param userId 用户id
     * @return SysUserInfoVo
     */
    SysUserInfoVo selectInfoById(String userId);

    /**
     * 更新密码
     *
     * @param userId      用户id
     * @param password    原来的密码
     * @param newPassword 新的密码
     */
    void updatePassword(String userId, String password, String newPassword);

    /**
     * 重置密码
     *
     * @param userId      用户id
     * @param newPassword 重置后的密码
     */
    void resetPassword(String userId, String newPassword);
}
