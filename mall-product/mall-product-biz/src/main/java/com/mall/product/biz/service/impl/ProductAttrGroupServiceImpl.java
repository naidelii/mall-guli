package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.mapper.ProductAttrGroupMapper;
import com.mall.product.biz.service.IProductAttrGroupService;
import com.mall.product.biz.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProductAttrGroupServiceImpl extends ServiceImpl<ProductAttrGroupMapper, ProductAttrGroup> implements IProductAttrGroupService {
    private final IProductCategoryService categoryService;

    @Override
    public IPage<ProductAttrGroupListVO> listAttrGroupWithPage(Integer pageNo, Integer pageSize, ProductAttrGroupQuery query) {
        Page<ProductAttrGroup> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductAttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getCategoryId()), ProductAttrGroup::getCategoryId, query.getCategoryId());
        String key = query.getKey();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and((wrapper ->
                    wrapper.like(ProductAttrGroup::getGroupName, key)
            ));
        }
        IPage<ProductAttrGroup> pageList = baseMapper.selectPage(page, queryWrapper);
        List<ProductAttrGroupListVO> userListVos = pageList.getRecords()
                .stream()
                .map(ProductAttrGroupListVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

    @Override
    public ProductAttrGroupVO getDetailsById(String id) {
        ProductAttrGroup productAttrGroup = baseMapper.selectById(id);
        ProductAttrGroupVO vo = new ProductAttrGroupVO(productAttrGroup);
        // 分类id
        String categoryId = productAttrGroup.getCategoryId();
        // 根据分类id查询出父级关系
        List<String> categoryIds = categoryService.getCategoryPathById(categoryId);
        String[] array = categoryIds.toArray(new String[0]);
        vo.setCategoryIds(array);
        return vo;
    }

    @Override
    public List<ProductAttrGroupListVO> listAttrGroupByCategoryId(String categoryId) {
        LambdaQueryWrapper<ProductAttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttrGroup::getCategoryId, categoryId);
        List<ProductAttrGroup> attrGroupList = baseMapper.selectList(queryWrapper);
        return attrGroupList.stream()
                .map(ProductAttrGroupListVO::new)
                .collect(Collectors.toList());
    }
}