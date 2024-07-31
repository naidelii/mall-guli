package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.entity.ProductCategoryBrand;
import com.mall.product.biz.domain.vo.ProductCategoryBrandListVO;
import com.mall.product.biz.mapper.ProductBrandMapper;
import com.mall.product.biz.mapper.ProductCategoryBrandMapper;
import com.mall.product.biz.mapper.ProductCategoryMapper;
import com.mall.product.biz.service.IProductCategoryBrandService;
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
public class ProductCategoryBrandServiceImpl extends ServiceImpl<ProductCategoryBrandMapper, ProductCategoryBrand> implements IProductCategoryBrandService {
    private final ProductCategoryMapper categoryMapper;
    private final ProductBrandMapper brandMapper;

    @Override
    public void saveDetail(ProductCategoryBrand data) {
        ProductBrand productBrand = brandMapper.selectById(data.getBrandId());
        data.setBrandName(productBrand.getName());
        ProductCategory productCategory = categoryMapper.selectById(data.getCategoryId());
        data.setCategoryName(productCategory.getCategoryName());
        baseMapper.insert(data);
    }

    @Override
    public List<ProductCategoryBrandListVO> listByBrandId(String brandId) {
        LambdaQueryWrapper<ProductCategoryBrand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCategoryBrand::getBrandId, brandId);
        List<ProductCategoryBrand> list = baseMapper.selectList(queryWrapper);
        return list.stream()
                .map(ProductCategoryBrandListVO::new)
                .collect(Collectors.toList());
    }
}