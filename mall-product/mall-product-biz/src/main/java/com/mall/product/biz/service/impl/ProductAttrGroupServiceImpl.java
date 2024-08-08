package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.entity.ProductAttributeGroups;
import com.mall.product.biz.domain.entity.ProductAttributes;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupWithAttrsVO;
import com.mall.product.biz.mapper.ProductAttributeGroupsMapper;
import com.mall.product.biz.mapper.ProductAttributesMapper;
import com.mall.product.biz.service.IProductAttributeGroupsService;
import com.mall.product.biz.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 商品属性分组
 *
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductAttrGroupServiceImpl extends ServiceImpl<ProductAttributeGroupsMapper, ProductAttributeGroups> implements IProductAttributeGroupsService {
    private final IProductCategoryService categoryService;
    private final ProductAttributesMapper attrMapper;

    @Override
    public IPage<ProductAttrGroupListVO> listAttrGroupWithPage(Integer pageNo, Integer pageSize, ProductAttrGroupQuery query) {
        Page<ProductAttributeGroups> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductAttributeGroups> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getCategoryId()), ProductAttributeGroups::getCategoryId, query.getCategoryId());
        String key = query.getKey();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and((wrapper ->
                    wrapper.like(ProductAttributeGroups::getGroupName, key)
            ));
        }
        IPage<ProductAttributeGroups> pageList = baseMapper.selectPage(page, queryWrapper);
        List<ProductAttrGroupListVO> userListVos = pageList.getRecords()
                .stream()
                .map(ProductAttrGroupListVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

    @Override
    public ProductAttrGroupVO getDetailsById(String id) {
        ProductAttributeGroups productAttrGroup = baseMapper.selectById(id);
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
        LambdaQueryWrapper<ProductAttributeGroups> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttributeGroups::getCategoryId, categoryId);
        List<ProductAttributeGroups> attrGroupList = baseMapper.selectList(queryWrapper);
        return attrGroupList.stream()
                .map(ProductAttrGroupListVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductAttrGroupWithAttrsVO> listAttrGroupWithAttrsByCategoryId(String categoryId) {
        // 1.根据分类id查询出所有的属性分组
        LambdaQueryWrapper<ProductAttributeGroups> attrGroupQueryWrapper = new LambdaQueryWrapper<>();
        attrGroupQueryWrapper.eq(ProductAttributeGroups::getCategoryId, categoryId);
        List<ProductAttributeGroups> attrGroupList = baseMapper.selectList(attrGroupQueryWrapper);
        if (attrGroupList.isEmpty()) {
            return Collections.emptyList();
        }
        // 所有的分组id
        Set<String> groupIds = attrGroupList.stream()
                .map(ProductAttributeGroups::getId)
                .collect(Collectors.toSet());
        // 查询出所有分组下的属性
        LambdaQueryWrapper<ProductAttributes> attributesQueryWrapper = new LambdaQueryWrapper<>();
        attributesQueryWrapper.in(ProductAttributes::getAttrGroupId, groupIds);
        List<ProductAttributes> productAttrs = attrMapper.selectList(attributesQueryWrapper);
        // 将属性分组和属性关联起来
        Map<String, List<ProductAttributes>> groupIdToAttrsMap = productAttrs.stream()
                .collect(Collectors.groupingBy(ProductAttributes::getAttrGroupId));
        return attrGroupList.stream()
                .map(group -> {
                    ProductAttrGroupWithAttrsVO vo = new ProductAttrGroupWithAttrsVO(group);
                    List<ProductAttributes> attrs = groupIdToAttrsMap.getOrDefault(group.getId(), Collections.emptyList());
                    vo.setAttrList(attrs);
                    return vo;
                }).collect(Collectors.toList());
    }

}