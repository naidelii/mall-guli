package com.mall.product.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrVO;
import com.mall.product.biz.mapper.ProductAttrGroupMapper;
import com.mall.product.biz.mapper.ProductAttrGroupRelationMapper;
import com.mall.product.biz.mapper.ProductAttrMapper;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductAttrGroupRelationService;
import com.mall.product.biz.service.IProductAttrService;
import com.mall.product.biz.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 商品属性表
 *
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductAttrServiceImpl extends ServiceImpl<ProductAttrMapper, ProductAttr> implements IProductAttrService {

    private final ProductCategoryMapper categoryMapper;
    private final IProductCategoryService categoryService;
    private final ProductAttrGroupRelationMapper attrGroupRelationMapper;
    private final IProductAttrGroupRelationService attrGroupRelationService;
    private final ProductAttrGroupMapper attrGroupMapper;

    @Override
    public IPage<ProductAttrListVO> listAttrWithPage(Integer pageNo, Integer pageSize, ProductAttrQuery query) {
        Page<ProductAttr> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductAttr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getCategoryId()), ProductAttr::getCategoryId, query.getCategoryId());
        String key = query.getKey();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and((wrapper ->
                    wrapper.like(ProductAttr::getAttrName, key)
                            .or()
                            .eq(ProductAttr::getId, key)
            ));
        }
        IPage<ProductAttr> pageList = baseMapper.selectPage(page, queryWrapper);
        // 查询其他信息
        List<ProductAttr> recordsList = page.getRecords();
        if (CollUtil.isEmpty(recordsList)) {
            return new Page<>();
        }
        // 分类id集合
        Set<String> categoryIds = recordsList.stream()
                .map(ProductAttr::getCategoryId)
                .collect(Collectors.toSet());
        // 查询分类信息
        Map<String, String> categoryMaps = categoryIds.isEmpty()
                ? Collections.emptyMap()
                : categoryMapper.selectBatchIds(categoryIds).stream()
                .collect(Collectors.toMap(
                        ProductCategory::getId,
                        ProductCategory::getCategoryName
                ));
        // 商品属性id
        Set<String> attrIds = recordsList.stream()
                .map(ProductAttr::getId)
                .collect(Collectors.toSet());
        List<ProductAttrGroupRelation> attrGroupRelations = attrIds.isEmpty()
                ? Collections.emptyList()
                : attrGroupRelationMapper.selectByAttrIds(attrIds);
        // 提取所有分组ID，并一次性查询所有分组
        Set<String> attrGroupIds = attrGroupRelations.stream()
                .map(ProductAttrGroupRelation::getAttrGroupId)
                .collect(Collectors.toSet());
        Map<String, String> attrGroupNames = attrGroupIds.isEmpty()
                ? Collections.emptyMap()
                : attrGroupMapper.selectBatchIds(attrGroupIds).stream()
                .collect(Collectors.toMap(
                        ProductAttrGroup::getId,
                        ProductAttrGroup::getGroupName
                ));
        // 生成属性ID到属性分组名称的映射
        Map<String, String> attrIdToGroupNameMap = attrGroupRelations.stream()
                .collect(Collectors.toMap(
                        ProductAttrGroupRelation::getAttrId,
                        relation -> attrGroupNames.get(relation.getAttrGroupId())
                ));
        List<ProductAttrListVO> listVos = buildVos(recordsList, categoryMaps, attrIdToGroupNameMap);
        return PageUtils.buildPage(listVos, pageList);
    }

    @Override
    public ProductAttrVO getDetailsById(String id) {
        ProductAttr data = baseMapper.selectById(id);
        ProductAttrVO vo = new ProductAttrVO(data);
        // 查询分类信息
        List<String> categoryPathById = categoryService.getCategoryPathById(data.getCategoryId());
        String[] array = categoryPathById.toArray(new String[0]);
        vo.setCategoryPath(array);
        // 查询分组信息
        ProductAttrGroupRelation relation = attrGroupRelationMapper.selectByAttrId(id);
        if (relation != null) {
            vo.setAttrGroupId(relation.getAttrGroupId());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(ProductAttr data, String attrGroupId) {
        baseMapper.updateById(data);
        if (StringUtils.isNotBlank(attrGroupId)) {
            attrGroupRelationService.saveOrUpdateAttrInfo(data.getId(), attrGroupId);
        }
    }

    private List<ProductAttrListVO> buildVos(List<ProductAttr> recordsList, Map<String, String> categoryMaps, Map<String, String> attrIdToGroupNameMap) {
        return recordsList.stream()
                .map(data -> {
                    ProductAttrListVO vo = new ProductAttrListVO(data);
                    vo.setCategoryName(categoryMaps.getOrDefault(data.getCategoryId(), ""));
                    vo.setAttrGroupName(attrIdToGroupNameMap.getOrDefault(data.getId(), ""));
                    return vo;
                })
                .collect(Collectors.toList());
    }


}