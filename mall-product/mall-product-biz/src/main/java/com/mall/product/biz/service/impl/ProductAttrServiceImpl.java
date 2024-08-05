package com.mall.product.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.constant.enums.ProductAttrEnum;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrRelationVO;
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
    public IPage<ProductAttrListVO> listAttrWithPage(Integer pageNo, Integer pageSize, ProductAttrQuery query, Integer attrType) {
        Page<ProductAttr> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductAttr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getCategoryId()), ProductAttr::getCategoryId, query.getCategoryId());
        queryWrapper.eq(ProductAttr::getAttrType, attrType);
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
        List<ProductAttrListVO> listVos = assembleListVO(recordsList, attrType);
        return PageUtils.buildPage(listVos, pageList);
    }

    private List<ProductAttrListVO> assembleListVO(List<ProductAttr> recordsList, Integer attrType) {
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
        // 如果是基本属性，则查询属性分组信息
        Map<String, String> attrIdToGroupNameMap = Collections.emptyMap();
        if (ProductAttrEnum.BASE.getType().equals(attrType)) {
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

            attrIdToGroupNameMap = attrGroupRelations.stream()
                    .collect(Collectors.toMap(
                            ProductAttrGroupRelation::getAttrId,
                            relation -> attrGroupNames.getOrDefault(relation.getAttrGroupId(), "")
                    ));

        }
        return buildVos(recordsList, categoryMaps, attrIdToGroupNameMap);
    }

    @Override
    public ProductAttrVO getDetailsById(String id) {
        ProductAttr data = baseMapper.selectById(id);
        ProductAttrVO vo = new ProductAttrVO(data);
        // 查询分类信息
        String categoryId = data.getCategoryId();
        if (StringUtils.isNotBlank(categoryId)) {
            List<String> categoryPathById = categoryService.getCategoryPathById(categoryId);
            String[] array = categoryPathById.toArray(new String[0]);
            vo.setCategoryPath(array);
        }
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

    @Override
    public void saveData(ProductAttr data, String attrGroupId) {
        baseMapper.insert(data);
        if (StringUtils.isNotBlank(attrGroupId)) {
            attrGroupRelationService.saveOrUpdateAttrInfo(data.getId(), attrGroupId);
        }
    }

    @Override
    public List<ProductAttrRelationVO> listAttrGroupsWithAttributes(String attrGroupId) {
        LambdaQueryWrapper<ProductAttrGroupRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttrGroupRelation::getAttrGroupId, attrGroupId);
        List<ProductAttrGroupRelation> groupRelations = attrGroupRelationMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupRelations)) {
            return Collections.emptyList();
        }
        // 所有的属性id
        Set<String> attrIds = groupRelations.stream()
                .map(ProductAttrGroupRelation::getAttrId)
                .collect(Collectors.toSet());
        List<ProductAttr> productAttrs = baseMapper.selectBatchIds(attrIds);
        if (CollUtil.isEmpty(productAttrs)) {
            return Collections.emptyList();
        }
        return productAttrs.stream()
                .map(ProductAttrRelationVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void removeRelation(String attrId, String attrGroupId) {
        LambdaQueryWrapper<ProductAttrGroupRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttrGroupRelation::getAttrGroupId, attrGroupId)
                .eq(ProductAttrGroupRelation::getAttrId, attrId);
        attrGroupRelationMapper.delete(queryWrapper);
    }

    @Override
    public IPage<ProductAttrRelationVO> listUnrelatedAttributes(Integer pageNo, Integer pageSize, String attrGroupId, String categoryId) {
        Page<ProductAttr> page = new Page<>(pageNo, pageSize);
        // 1.根据分类id查询该属性分组信息
        LambdaQueryWrapper<ProductAttrGroup> attrGroupQueryWrapper = new LambdaQueryWrapper<>();
        attrGroupQueryWrapper.eq(ProductAttrGroup::getCategoryId, categoryId);
        List<ProductAttrGroup> attrGroupList = attrGroupMapper.selectList(attrGroupQueryWrapper);
        // 当前分类下所有的属性分组id
        Set<String> attrGroupIds = attrGroupList.stream()
                .map(ProductAttrGroup::getId)
                .collect(Collectors.toSet());
        LambdaQueryWrapper<ProductAttrGroupRelation> attrGroupRelationQueryWrapper = new LambdaQueryWrapper<>();
        attrGroupRelationQueryWrapper.in(ProductAttrGroupRelation::getAttrGroupId, attrGroupIds);
        List<ProductAttrGroupRelation> attrGroupRelations = attrGroupRelationMapper.selectList(attrGroupRelationQueryWrapper);
        // 已经被关联的属性
        Set<String> attrIds = attrGroupRelations.stream()
                .map(ProductAttrGroupRelation::getAttrId)
                .collect(Collectors.toSet());
        LambdaQueryWrapper<ProductAttr> attrQueryWrapper = new LambdaQueryWrapper<>();
        attrQueryWrapper.eq(ProductAttr::getCategoryId, categoryId)
                .eq(ProductAttr::getAttrType, ProductAttrEnum.BASE.getType())
                .notIn(ProductAttr::getId, attrIds);
        Page<ProductAttr> pageList = baseMapper.selectPage(page, attrQueryWrapper);
        List<ProductAttrRelationVO> listVos = pageList.getRecords()
                .stream()
                .map(ProductAttrRelationVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(listVos, pageList);
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