package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.mall.common.base.api.Result;
import com.mall.product.biz.domain.dto.ProductCategoryBrandSaveDTO;
import com.mall.product.biz.domain.entity.ProductCategoryBrand;
import com.mall.product.biz.domain.vo.ProductCategoryBrandListVO;
import com.mall.product.biz.service.IProductCategoryBrandService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/listByBrandId")
    public Result<List<ProductCategoryBrandListVO>> listByBrandId(@RequestParam("brandId") String brandId) {
        List<ProductCategoryBrandListVO> list = productCategoryBrandService.listByBrandId(brandId);
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody ProductCategoryBrandSaveDTO saveDto) {
        ProductCategoryBrand data = new ProductCategoryBrand();
        BeanUtil.copyProperties(saveDto, data);
        productCategoryBrandService.saveDetail(data);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam String id) {
        productCategoryBrandService.removeById(id);
        return Result.success();
    }
}
