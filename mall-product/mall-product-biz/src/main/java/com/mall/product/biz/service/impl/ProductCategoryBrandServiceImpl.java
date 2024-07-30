package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductCategoryBrand;
import com.mall.product.biz.mapper.ProductCategoryBrandMapper;
import com.mall.product.biz.service.IProductCategoryBrandService;
import org.springframework.stereotype.Service;


/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Service
public class ProductCategoryBrandServiceImpl extends ServiceImpl<ProductCategoryBrandMapper, ProductCategoryBrand> implements IProductCategoryBrandService {

}