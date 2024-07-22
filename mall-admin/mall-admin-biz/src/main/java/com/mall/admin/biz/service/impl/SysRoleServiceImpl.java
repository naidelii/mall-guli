package com.mall.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.vo.SysRoleListVo;
import com.mall.admin.biz.mapper.SysRoleMapper;
import com.mall.admin.biz.service.ISysRoleService;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public List<SysRole> selectRolesByUserId(String userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    @Override
    public IPage<SysRoleListVo> selectListPage(Integer pageNo, Integer pageSize, SysRoleListQuery query) {
        IPage<SysRole> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getRoleName()), SysRole::getRoleName, query.getRoleName());
        IPage<SysRole> pageList = baseMapper.selectPage(page, queryWrapper);
        List<SysRoleListVo> userListVos = pageList.getRecords()
                .stream()
                .map(SysRoleListVo::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

}
