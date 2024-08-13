package com.mall.product.biz.controller;


import com.mall.product.biz.service.IProductSpuInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * spu信息
 *
 * @author naidelii
 */
@Api(tags = "spu信息")
@Slf4j
@RequestMapping("/product/spuInfo")
@RestController
@RequiredArgsConstructor
public class ProductSpuInfoController {

    private final IProductSpuInfoService productSpuInfoService;


}
