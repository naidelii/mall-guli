package com.mall.product.biz.controller;


import com.mall.product.biz.service.IProductAttrService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品属性表
 *
 * @author naidelii
 */
@Api(tags = "商品属性表")
@Slf4j
@RequestMapping("/product/attr")
@RestController
@RequiredArgsConstructor
public class ProductAttrController {

    private final IProductAttrService productAttrService;


}
