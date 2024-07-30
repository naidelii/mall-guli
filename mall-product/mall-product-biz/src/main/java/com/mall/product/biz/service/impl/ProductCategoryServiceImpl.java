package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductCategoryListTreeVO;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author naidelii
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    @Override
    public List<ProductCategoryListTreeVO> listWithTree() {
        List<ProductCategory> productCategoryList = baseMapper.selectList(null);
        productCategoryList.sort(Comparator.comparingInt(ProductCategory::getSortOrder).reversed());
        Map<String, List<ProductCategory>> parentIdToChildrenMap = productCategoryList.stream()
                .collect(Collectors.groupingBy(ProductCategory::getParentId));
        // 获取顶级分类
        return parentIdToChildrenMap.getOrDefault(CommonConstants.PARENT_CODE, Collections.emptyList())
                .stream()
                .map(vo -> convertToVo(vo, parentIdToChildrenMap))
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByIds(List<String> categoryIds) {
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

    private List<ProductCategoryListTreeVO> getChildren(String parentId, Map<String, List<ProductCategory>> parentIdToChildrenMap) {
        return parentIdToChildrenMap.getOrDefault(parentId, Collections.emptyList())
                .stream()
                .map(vo -> convertToVo(vo, parentIdToChildrenMap))
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }

    private ProductCategoryListTreeVO convertToVo(ProductCategory entity, Map<String, List<ProductCategory>> parentIdToChildrenMap) {
        ProductCategoryListTreeVO vo = new ProductCategoryListTreeVO(entity);
        List<ProductCategoryListTreeVO> children = getChildren(entity.getId(), parentIdToChildrenMap);
        vo.setChildren(children);
        return vo;
    }

}