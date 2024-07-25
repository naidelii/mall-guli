package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.mapper.ProductBrandMapper;
import com.mall.product.biz.service.IProductBrandService;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Service
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements IProductBrandService {

}