package com.mall.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Naidelii
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 根据用户名，查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Select("select * from sys_user where username = #{username}")
    SysUser queryByUserName(@Param("username") String username);
}
