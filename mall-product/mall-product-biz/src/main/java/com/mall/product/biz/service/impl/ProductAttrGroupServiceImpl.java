package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.mapper.ProductAttrGroupMapper;
import com.mall.product.biz.service.IProductAttrGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 商品属性分组
 *
 * @author naidelii
 */
@Service
public class ProductAttrGroupServiceImpl extends ServiceImpl<ProductAttrGroupMapper, ProductAttrGroup> implements IProductAttrGroupService {

    @Override
    public IPage<ProductAttrGroupListVO> listAttrGroupWithPage(Integer pageNo, Integer pageSize, ProductAttrGroupQuery query) {
        Page<ProductAttrGroup> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductAttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getCategoryId()), ProductAttrGroup::getCategoryId, query.getCategoryId());
        if (StringUtils.isNotBlank(query.getKey())) {
            queryWrapper.and((obj) -> obj.like(ProductAttrGroup::getGroupName, query.getKey()));
        }
        IPage<ProductAttrGroup> pageList = baseMapper.selectPage(page, queryWrapper);
        List<ProductAttrGroupListVO> userListVos = pageList.getRecords()
                .stream()
                .map(ProductAttrGroupListVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }
}