package com.mall.admin.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.admin.api.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author naidelii
 */
@Data
public class SysUserListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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

    public SysUserListVo(SysUser sysUser) {
        BeanUtil.copyProperties(sysUser, this);
    }
}
