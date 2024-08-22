package com.mall.product.biz.converter;

import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductCategoryListTreeVO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author naidelii
 */
public final class ProductCategoryConverter {

    private ProductCategoryConverter() {

    }

    public static List<ProductCategoryListTreeVO> buildCategoryTree(List<ProductCategory> list) {
        // 转为map结构
        Map<String, List<ProductCategory>> parentIdToChildrenMap = list.stream()
                .collect(Collectors.groupingBy(ProductCategory::getParentId));
        // 获取顶级分类
        return parentIdToChildrenMap.getOrDefault(CommonConstants.PARENT_CODE, Collections.emptyList())
                .stream()
                .map(vo -> convertToVo(vo, parentIdToChildrenMap))
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }

    private static ProductCategoryListTreeVO convertToVo(ProductCategory entity, Map<String, List<ProductCategory>> parentIdToChildrenMap) {
        ProductCategoryListTreeVO vo = new ProductCategoryListTreeVO(entity);
        List<ProductCategoryListTreeVO> children = getChildren(entity.getId(), parentIdToChildrenMap);
        vo.setChildren(children);
        return vo;
    }

    private static List<ProductCategoryListTreeVO> getChildren(String parentId, Map<String, List<ProductCategory>> parentIdToChildrenMap) {
        return parentIdToChildrenMap.getOrDefault(parentId, Collections.emptyList())
                .stream()
                .map(vo -> convertToVo(vo, parentIdToChildrenMap))
                .sorted((a, b) -> a.getSortOrder() - b.getSortOrder())
                .collect(Collectors.toList());
    }


}
