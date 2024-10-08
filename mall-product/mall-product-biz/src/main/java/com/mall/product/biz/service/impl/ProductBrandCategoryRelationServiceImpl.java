package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.domain.entity.ProductBrandCategoryRelation;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductBrandCategoryRelationListVO;
import com.mall.product.biz.mapper.ProductBrandCategoryRelationMapper;
import com.mall.product.biz.mapper.ProductBrandMapper;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductBrandCategoryRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductBrandCategoryRelationServiceImpl extends ServiceImpl<ProductBrandCategoryRelationMapper, ProductBrandCategoryRelation> implements IProductBrandCategoryRelationService {
    private final ProductCategoryMapper categoryMapper;
    private final ProductBrandMapper brandMapper;

    @Override
    public void saveDetail(ProductBrandCategoryRelation data) {
        ProductBrand productBrand = brandMapper.selectById(data.getBrandId());
        data.setBrandName(productBrand.getBrandName());
        ProductCategory productCategory = categoryMapper.selectById(data.getCategoryId());
        data.setCategoryName(productCategory.getCategoryName());
        baseMapper.insert(data);
    }

    @Override
    public List<ProductBrandCategoryRelationListVO> listByBrandId(String brandId) {
        LambdaQueryWrapper<ProductBrandCategoryRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductBrandCategoryRelation::getBrandId, brandId);
        List<ProductBrandCategoryRelation> list = baseMapper.selectList(queryWrapper);
        return list.stream()
                .map(ProductBrandCategoryRelationListVO::new)
                .collect(Collectors.toList());
    }
}