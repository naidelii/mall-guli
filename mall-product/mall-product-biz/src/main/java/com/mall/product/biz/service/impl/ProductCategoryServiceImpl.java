package com.mall.product.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.constant.CacheConstants;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.exception.GlobalException;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.mapper.ProductBrandCategoryRelationMapper;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    private final ProductBrandCategoryRelationMapper brandCategoryRelationMapper;

    @Override
    @Cacheable(value = CacheConstants.PRODUCT_CATEGORY_LIST_CACHE, key = CacheConstants.ALL_DATA_CACHE_KEY, unless = "#result == null || #result.isEmpty()")
    public List<ProductCategory> listAllData() {
        List<ProductCategory> list = baseMapper.selectList(null);
        // 进行排序
        list.sort(Comparator.comparingInt(ProductCategory::getSortOrder).reversed());
        return list;
    }

    @Override
    @CacheEvict(value = CacheConstants.PRODUCT_CATEGORY_LIST_CACHE, allEntries = true)
    public void deleteById(String id) {
        // 判断是否还有子菜单
        List<ProductCategory> list = baseMapper.listByParentId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new GlobalException("该分类下还有子分类数据");
        }
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(Set<String> categoryIds) {
        // 批量删除
        baseMapper.deleteBatchIds(categoryIds);
    }

    @Override
    public List<String> getCategoryPathById(String id) {
        List<String> categoryPath = new ArrayList<>();
        getCategoryPathRecursive(id, categoryPath);
        Collections.reverse(categoryPath);
        return categoryPath;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(ProductCategory data) {
        String categoryName = data.getCategoryName();
        if (StringUtils.isNotBlank(categoryName)) {
            brandCategoryRelationMapper.updateCategory(data.getId(), categoryName);
        }
        baseMapper.updateById(data);
    }


    private void getCategoryPathRecursive(String id, List<String> categoryPath) {
        // 防止无限递归
        if (categoryPath.contains(id)) {
            return;
        }
        ProductCategory productCategory = baseMapper.selectById(id);
        if (productCategory != null) {
            categoryPath.add(productCategory.getId());
            // 如果parentId不是0，递归查找上一级分类
            if (!CommonConstants.PARENT_CODE.equals(productCategory.getParentId())) {
                getCategoryPathRecursive(productCategory.getParentId(), categoryPath);
            }
        }
    }


}