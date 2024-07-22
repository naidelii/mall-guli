package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.vo.SysUserListVo;
import com.mall.admin.biz.mapper.SysUserMapper;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.data.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public IPage<SysUserListVo> selectListPage(Integer pageNo, Integer pageSize, SysUserListQuery query) {
        IPage<SysUser> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername());
        IPage<SysUser> pageList = baseMapper.selectPage(page, queryWrapper);
        List<SysUserListVo> userListVos = pageList.getRecords()
                .stream()
                .map(SysUserListVo::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

}