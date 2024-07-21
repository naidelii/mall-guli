package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.mapper.SysUserMapper;
import com.mall.admin.biz.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Override
    public SysUser queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    public SysUserInfoVo getUserInfo(String userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        return new SysUserInfoVo(sysUser);
    }
}