package com.mall.product.biz.controller;


import com.mall.product.biz.service.IProductBrandService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 品牌表
 *
 * @author naidelii
 */
@Api(tags = "品牌表")
@Slf4j
@RequestMapping("product/brand")
@RestController
@RequiredArgsConstructor
public class ProductBrandController {

    private final IProductBrandService productBrandService;


}
