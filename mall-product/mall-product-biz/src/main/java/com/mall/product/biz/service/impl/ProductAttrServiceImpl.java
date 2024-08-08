package com.mall.product.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.constant.enums.ProductAttrEnum;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.entity.ProductAttributes;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrRelationVO;
import com.mall.product.biz.domain.vo.ProductAttrVO;
import com.mall.product.biz.mapper.ProductAttrGroupMapper;
import com.mall.product.biz.mapper.ProductAttributesMapper;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductAttributesService;
import com.mall.product.biz.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 商品属性表
 *
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductAttrServiceImpl extends ServiceImpl<ProductAttributesMapper, ProductAttributes> implements IProductAttributesService {

    private final ProductCategoryMapper categoryMapper;
    private final IProductCategoryService categoryService;
    private final ProductAttrGroupMapper attrGroupMapper;

    @Override
    public IPage<ProductAttrListVO> listAttrWithPage(Integer pageNo, Integer pageSize, ProductAttrQuery query, Integer attrType) {
        Page<ProductAttributes> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductAttributes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getCategoryId()), ProductAttributes::getCategoryId, query.getCategoryId());
        queryWrapper.eq(ProductAttributes::getAttrType, attrType);
        String key = query.getKey();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and((wrapper ->
                    wrapper.like(ProductAttributes::getAttrName, key)
                            .or()
                            .eq(ProductAttributes::getId, key)
            ));
        }
        IPage<ProductAttributes> pageList = baseMapper.selectPage(page, queryWrapper);
        // 查询其他信息
        List<ProductAttributes> recordsList = page.getRecords();
        if (CollUtil.isEmpty(recordsList)) {
            return new Page<>();
        }
        List<ProductAttrListVO> listVos = assembleListVO(recordsList);
        return PageUtils.buildPage(listVos, pageList);
    }

    private List<ProductAttrListVO> assembleListVO(List<ProductAttributes> recordsList) {
        // 分类id集合
        Set<String> categoryIds = recordsList.stream()
                .map(ProductAttributes::getCategoryId)
                .collect(Collectors.toSet());
        // 查询分类信息
        Map<String, String> categoryMaps = categoryIds.isEmpty()
                ? Collections.emptyMap()
                : categoryMapper.selectBatchIds(categoryIds).stream()
                .collect(Collectors.toMap(
                        ProductCategory::getId,
                        ProductCategory::getCategoryName
                ));
        // 获取分组信息
        Set<String> groupIds = recordsList.stream()
                .map(ProductAttributes::getAttrGroupId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
        Map<String, String> attrIdToGroupNameMap;
        if (groupIds.isEmpty()) {
            attrIdToGroupNameMap = Collections.emptyMap();
        } else {
            List<ProductAttrGroup> productAttrGroups = attrGroupMapper.selectBatchIds(groupIds);
            attrIdToGroupNameMap = productAttrGroups.stream()
                    .collect(Collectors.toMap(
                            ProductAttrGroup::getId,
                            ProductAttrGroup::getGroupName
                    ));
        }
        return buildVos(recordsList, categoryMaps, attrIdToGroupNameMap);
    }

    @Override
    public ProductAttrVO getDetailsById(String id) {
        ProductAttributes data = baseMapper.selectById(id);
        ProductAttrVO vo = new ProductAttrVO(data);
        // 查询分类信息
        String categoryId = data.getCategoryId();
        if (StringUtils.isNotBlank(categoryId)) {
            List<String> categoryPathById = categoryService.getCategoryPathById(categoryId);
            String[] array = categoryPathById.toArray(new String[0]);
            vo.setCategoryPath(array);
        }
        return vo;
    }

    @Override
    public void updateData(ProductAttributes data) {
        baseMapper.updateById(data);
    }

    @Override
    public void saveData(ProductAttributes data) {
        baseMapper.insert(data);
    }

    @Override
    public List<ProductAttrRelationVO> listAttrGroupsWithAttributes(String attrGroupId) {
        // 根据属性属性分组id查询出对应的属性信息
        LambdaQueryWrapper<ProductAttributes> attributesQueryWrapper = new LambdaQueryWrapper<>();
        attributesQueryWrapper
                .eq(ProductAttributes::getAttrType, ProductAttrEnum.BASE.getType())
                .eq(ProductAttributes::getAttrGroupId, attrGroupId);
        List<ProductAttributes> productAttributes = baseMapper.selectList(attributesQueryWrapper);
        return productAttributes.stream()
                .map(ProductAttrRelationVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<ProductAttrRelationVO> listUnrelatedAttributes(Integer pageNo, Integer pageSize, String attrGroupId, String categoryId) {
        Page<ProductAttributes> page = new Page<>(pageNo, pageSize);
        // 1.找到当前分类下已经被属性分组关联的属性
        LambdaQueryWrapper<ProductAttributes> attrQueryWrapper = new LambdaQueryWrapper<>();
        attrQueryWrapper.eq(ProductAttributes::getCategoryId, categoryId)
                .eq(ProductAttributes::getAttrType, ProductAttrEnum.BASE.getType())
                .eq(ProductAttributes::getAttrGroupId, StringUtils.EMPTY);
        Page<ProductAttributes> pageList = baseMapper.selectPage(page, attrQueryWrapper);
        List<ProductAttrRelationVO> listVos = pageList.getRecords()
                .stream()
                .map(ProductAttrRelationVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(listVos, pageList);
    }


    private List<ProductAttrListVO> buildVos(List<ProductAttributes> recordsList, Map<String, String> categoryMaps, Map<String, String> attrIdToGroupNameMap) {
        return recordsList.stream()
                .map(data -> {
                    ProductAttrListVO vo = new ProductAttrListVO(data);
                    vo.setCategoryName(categoryMaps.getOrDefault(data.getCategoryId(), ""));
                    vo.setAttrGroupName(attrIdToGroupNameMap.getOrDefault(data.getAttrGroupId(), ""));
                    return vo;
                })
                .collect(Collectors.toList());
    }


}