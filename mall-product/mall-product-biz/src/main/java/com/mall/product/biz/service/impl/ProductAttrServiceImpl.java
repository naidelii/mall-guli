package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.mapper.ProductAttrMapper;
import com.mall.product.biz.service.IProductAttrService;
import org.springframework.stereotype.Service;


/**
 * 商品属性表
 *
 * @author naidelii
 */
@Service
public class ProductAttrServiceImpl extends ServiceImpl<ProductAttrMapper, ProductAttr> implements IProductAttrService {

}