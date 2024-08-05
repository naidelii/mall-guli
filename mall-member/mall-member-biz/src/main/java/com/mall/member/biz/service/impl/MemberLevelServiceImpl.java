package com.mall.member.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.member.biz.domain.dto.MemberLevelQuery;
import com.mall.member.biz.domain.vo.MemberLevelListVO;
import com.mall.member.biz.entity.MemberLevel;
import com.mall.member.biz.mapper.MemberLevelMapper;
import com.mall.member.biz.service.IMemberLevelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 会员等级
 *
 * @author naidelii
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements IMemberLevelService {

    @Override
    public IPage<MemberLevelListVO> selectListPage(Integer pageNo, Integer pageSize, MemberLevelQuery query) {
        Page<MemberLevel> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<MemberLevel> queryWrapper = new LambdaQueryWrapper<>();
        String key = query.getKey();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and((wrapper ->
                    wrapper.like(MemberLevel::getName, key)
                            .or()
                            .eq(MemberLevel::getId, key)
            ));
        }
        IPage<MemberLevel> pageList = baseMapper.selectPage(page, queryWrapper);
        List<MemberLevelListVO> userListVos = pageList.getRecords()
                .stream()
                .map(MemberLevelListVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }
}