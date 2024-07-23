package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductCategoryListTreeVo;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author naidelii
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    @Override
    public List<ProductCategoryListTreeVo> listWithTree() {
        List<ProductCategory> productCategoryList = baseMapper.selectList(null);
        productCategoryList.sort(Comparator.comparingInt(ProductCategory::getSortOrder).reversed());
        Map<String, List<ProductCategory>> parentIdToChildrenMap = productCategoryList.stream()
                .collect(Collectors.groupingBy(ProductCategory::getParentId));
        // 获取顶级分类
        return parentIdToChildrenMap.getOrDefault(CommonConstants.PARENT_CODE, Collections.emptyList())
                .stream()
                .map(vo -> convertToVo(vo, parentIdToChildrenMap))
                .collect(Collectors.toList());
    }

    private List<ProductCategoryListTreeVo> getChildren(String parentId, Map<String, List<ProductCategory>> parentIdToChildrenMap) {
        return parentIdToChildrenMap.getOrDefault(parentId, Collections.emptyList())
                .stream()
                .map(vo -> convertToVo(vo, parentIdToChildrenMap))
                .collect(Collectors.toList());
    }

    private ProductCategoryListTreeVo convertToVo(ProductCategory entity, Map<String, List<ProductCategory>> parentIdToChildrenMap) {
        ProductCategoryListTreeVo vo = new ProductCategoryListTreeVo(entity);
        List<ProductCategoryListTreeVo> children = getChildren(entity.getId(), parentIdToChildrenMap);
        vo.setChildren(children);
        return vo;
    }

}