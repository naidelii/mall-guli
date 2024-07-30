package com.mall.product.biz.controller;


import com.mall.product.biz.service.IProductAttrGroupService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品属性分组
 *
 * @author naidelii
 */
@Api(tags = "商品属性分组")
@Slf4j
@RequestMapping("/product/attrGroup")
@RestController
@RequiredArgsConstructor
public class ProductAttrGroupController {

    private final IProductAttrGroupService productAttrGroupService;


}
