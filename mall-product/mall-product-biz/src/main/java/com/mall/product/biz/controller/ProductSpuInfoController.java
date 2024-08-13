package com.mall.product.biz.controller;


import com.mall.common.base.api.Result;
import com.mall.product.biz.domain.dto.ProductSpuSaveDTO;
import com.mall.product.biz.service.IProductSpuInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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

    @PostMapping("/save")
    public Result<?> save(@Valid @RequestBody ProductSpuSaveDTO saveDto) {
        System.out.println(saveDto);
        return Result.success();
    }
}
