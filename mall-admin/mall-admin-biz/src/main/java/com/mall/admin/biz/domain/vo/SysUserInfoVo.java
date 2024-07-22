package com.mall.admin.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.admin.api.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author naidelii
 */
@Data
public class SysUserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * md5密码盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态（1：正常，0：禁用）
     */
    private Integer status;

    /**
     * 性别(0：未知，1：男，2：女)
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 出生日期
     */
    private LocalDateTime birthdate;

    /**
     * 角色列表
     */
    private Set<String> roleIds;

    public SysUserInfoVo(SysUser sysUser) {
        BeanUtil.copyProperties(sysUser, this);
    }
}
