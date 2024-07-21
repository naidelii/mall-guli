package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.mapper.SysUserMapper;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.data.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public IPage<SysUserInfoVo> selectListPage(Page<SysUser> page) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        Page<SysUser> pageList = baseMapper.selectPage(page, queryWrapper);
        List<SysUserInfoVo> resultList = pageList.getRecords()
                .stream()
                .map(SysUserInfoVo::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(resultList, pageList);
    }
}