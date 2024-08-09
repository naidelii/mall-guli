package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.biz.domain.entity.LoginUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Naidelii
 */
public interface LoginUserMapper extends BaseMapper<LoginUserInfo> {


    /**
     * 根据用户名，查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    LoginUserInfo queryByUserName(@Param("username") String username);

    /**
     * 根据用户id，查询拥有拥有的角色
     *
     * @param userId 用户id
     * @return 角色信息
     */
    List<String> selectRolesByUserId(@Param("userId") String userId);

    /**
     * 查询所有的权限
     *
     * @return 权限信息
     */
    List<String> listAllPermission();

    /**
     * 根据用户id查询出拥有的权限
     *
     * @param userId 用户id
     * @return 权限信息
     */
    List<String> listPermsByUserId(@Param("userId") String userId);
}
