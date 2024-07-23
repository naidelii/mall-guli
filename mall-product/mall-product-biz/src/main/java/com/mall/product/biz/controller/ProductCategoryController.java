package com.mall.product.biz.controller;

import com.mall.common.base.api.Result;
import com.mall.product.biz.domain.vo.ProductCategoryListTreeVo;
import com.mall.product.biz.service.IProductCategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
@Api(tags = "商品分类表")
@Slf4j
@RequestMapping("/product/category")
@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

    private final IProductCategoryService productCategoryService;

    @GetMapping("/listWithTree")
    public Result<List<ProductCategoryListTreeVo>> listWithTree() {
        return Result.success(productCategoryService.listWithTree());
    }
}
