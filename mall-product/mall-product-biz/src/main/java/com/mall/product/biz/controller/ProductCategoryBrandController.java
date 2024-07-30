package com.mall.product.biz.controller;


import com.mall.product.biz.service.IProductCategoryBrandService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Api(tags = "品牌分类关联表")
@Slf4j
@RequestMapping("/product/categoryBrand")
@RestController
@RequiredArgsConstructor
public class ProductCategoryBrandController {

    private final IProductCategoryBrandService productCategoryBrandService;


}
