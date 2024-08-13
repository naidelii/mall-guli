package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductSkuInfo;
import com.mall.product.biz.mapper.ProductSkuInfoMapper;
import com.mall.product.biz.service.IProductSkuInfoService;
import org.springframework.stereotype.Service;


/**
 * sku信息
 *
 * @author naidelii
 */
@Service
public class ProductSkuInfoServiceImpl extends ServiceImpl<ProductSkuInfoMapper, ProductSkuInfo> implements IProductSkuInfoService {

}