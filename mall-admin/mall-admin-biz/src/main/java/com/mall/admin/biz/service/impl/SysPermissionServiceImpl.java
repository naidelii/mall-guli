package com.mall.admin.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.mapper.SysPermissionMapper;
import com.mall.admin.biz.service.ISysPermissionService;
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
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermissionListVo> selectPermissionList() {
        List<SysPermission> menuList = baseMapper.selectPermissionListByType(null);
        return menuList.stream()
                .map(SysPermissionListVo::new)
                .collect(Collectors.toList());
    }

}
