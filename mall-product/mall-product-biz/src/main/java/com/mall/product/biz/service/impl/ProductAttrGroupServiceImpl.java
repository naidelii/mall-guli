package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupWithAttrsVO;
import com.mall.product.biz.mapper.ProductAttrGroupMapper;
import com.mall.product.biz.mapper.ProductAttrGroupRelationMapper;
import com.mall.product.biz.mapper.ProductAttrMapper;
import com.mall.product.biz.service.IProductAttrGroupService;
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
public class ProductAttrGroupServiceImpl extends ServiceImpl<ProductAttrGroupMapper, ProductAttrGroup> implements IProductAttrGroupService {
    private final IProductCategoryService categoryService;
    private final ProductAttrGroupRelationMapper groupRelationMapper;
    private final ProductAttrMapper attrMapper;

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

    @Override
    public List<ProductAttrGroupWithAttrsVO> listAttrGroupWithAttrsByCategoryId(String categoryId) {
        // 1.根据分类id查询出所有的属性分组
        LambdaQueryWrapper<ProductAttrGroup> attrGroupQueryWrapper = new LambdaQueryWrapper<>();
        attrGroupQueryWrapper.eq(ProductAttrGroup::getCategoryId, categoryId);
        List<ProductAttrGroup> attrGroupList = baseMapper.selectList(attrGroupQueryWrapper);
        if (attrGroupList.isEmpty()) {
            return Collections.emptyList();
        }
        // 所有的分组id
        Set<String> groupIds = attrGroupList.stream()
                .map(ProductAttrGroup::getId)
                .collect(Collectors.toSet());
        // 查询出所有分组下的属性
        LambdaQueryWrapper<ProductAttrGroupRelation> attrGroupRelationQueryWrapper = new LambdaQueryWrapper<>();
        attrGroupRelationQueryWrapper.in(ProductAttrGroupRelation::getAttrGroupId, groupIds);
        List<ProductAttrGroupRelation> attrGroupRelations = groupRelationMapper.selectList(attrGroupRelationQueryWrapper);
        if (attrGroupRelations.isEmpty()) {
            return Collections.emptyList();
        }
        // 所有的属性id
        Set<String> attrIds = attrGroupRelations.stream()
                .map(ProductAttrGroupRelation::getAttrId)
                .collect(Collectors.toSet());
        // 相关的属性信息
        List<ProductAttr> productAttrs = attrMapper.selectBatchIds(attrIds);
        // 将属性分组和属性关联起来
        Map<String, List<ProductAttr>> groupIdToAttrsMap = attrGroupRelations.stream()
                .collect(Collectors.groupingBy(
                        ProductAttrGroupRelation::getAttrGroupId,
                        Collectors.mapping(
                                relation -> productAttrs.stream()
                                        .filter(attr -> attr.getId().equals(relation.getAttrId()))
                                        .findFirst()
                                        .orElse(null),
                                Collectors.toList()
                        )
                ));
        return attrGroupList.stream()
                .map(group -> {
                    ProductAttrGroupWithAttrsVO vo = new ProductAttrGroupWithAttrsVO(group);
                    List<ProductAttr> attrs = groupIdToAttrsMap.getOrDefault(group.getId(), Collections.emptyList());
                    vo.setAttrList(attrs);
                    return vo;
                }).collect(Collectors.toList());
    }

}