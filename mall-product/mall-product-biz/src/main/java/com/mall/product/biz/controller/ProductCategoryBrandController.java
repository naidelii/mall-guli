package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.mall.common.base.api.Result;
import com.mall.product.biz.domain.dto.ProductBrandCategoryRelationSaveDTO;
import com.mall.product.biz.domain.entity.ProductBrandCategoryRelation;
import com.mall.product.biz.domain.vo.ProductBrandCategoryRelationListVO;
import com.mall.product.biz.service.IProductBrandCategoryRelationService;
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

    private final IProductBrandCategoryRelationService brandCategoryRelationService;

    @GetMapping("/listByBrandId")
    public Result<List<ProductBrandCategoryRelationListVO>> listByBrandId(@RequestParam("brandId") String brandId) {
        List<ProductBrandCategoryRelationListVO> list = brandCategoryRelationService.listByBrandId(brandId);
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody ProductBrandCategoryRelationSaveDTO saveDto) {
        ProductBrandCategoryRelation data = new ProductBrandCategoryRelation();
        BeanUtil.copyProperties(saveDto, data);
        brandCategoryRelationService.saveDetail(data);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam String id) {
        brandCategoryRelationService.removeById(id);
        return Result.success();
    }
}
