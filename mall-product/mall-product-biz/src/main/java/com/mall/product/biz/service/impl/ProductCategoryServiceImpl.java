package com.mall.product.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.constant.CacheConstants;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.exception.GlobalException;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductCategoryVo;
import com.mall.product.biz.event.ProductCategoryUpdateEvent;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    private final ApplicationEventPublisher publisher;

    @Override
    @Cacheable(value = CacheConstants.PRODUCT_CATEGORY_LIST_CACHE, key = CacheConstants.ALL_DATA_CACHE_KEY, unless = "#result == null || #result.isEmpty()")
    public List<ProductCategory> listAllData() {
        List<ProductCategory> list = baseMapper.selectList(null);
        // 进行排序
        list.sort(Comparator.comparingInt(ProductCategory::getSortOrder).reversed());
        return list;
    }

    @Override
    public ProductCategoryVo getDetailsById(String id) {
        return baseMapper.getDetailsById(id);
    }

    @Override
    @CacheEvict(value = CacheConstants.PRODUCT_CATEGORY_LIST_CACHE, allEntries = true)
    public void saveData(ProductCategory data) {
        // 设置层级并保存数据
        int level = calculationHierarchy(data.getParentId());
        data.setLevel(level);
        baseMapper.insert(data);
    }

    @Override
    @CacheEvict(value = CacheConstants.PRODUCT_CATEGORY_LIST_CACHE, allEntries = true)
    public void updateData(ProductCategory data) {
        // 计算层级并设置
        data.setLevel(calculationHierarchy(data.getParentId()));
        // 更新数据
        baseMapper.updateById(data);
        // 发布更新事件
        publisher.publishEvent(new ProductCategoryUpdateEvent(this, data));
    }

    private int calculationHierarchy(String parentId) {
        // 默认将层级设置为初始等级
        if (CommonConstants.PARENT_CODE.equals(parentId)) {
            return CommonConstants.DEFAULT_CATEGORY_LEVEL;
        }
        // 如果不是顶级分类，计算层级
        ProductCategory parentData = baseMapper.selectById(parentId);
        if (parentData == null) {
            throw new GlobalException("父分类不存在！");
        }
        return parentData.getLevel() + 1;
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