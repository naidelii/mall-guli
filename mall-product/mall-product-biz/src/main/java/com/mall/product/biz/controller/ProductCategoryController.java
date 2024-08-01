package com.mall.product.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mall.common.base.api.Result;
import com.mall.product.biz.domain.dto.ProductCategorySaveDTO;
import com.mall.product.biz.domain.dto.ProductCategoryUpdateDTO;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductCategoryListTreeVO;
import com.mall.product.biz.service.IProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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
    @ApiOperation("商品分类列表-树形结构")
    public Result<List<ProductCategoryListTreeVO>> listWithTree() {
        List<ProductCategoryListTreeVO> listTreeVos = productCategoryService.listWithTree();
        return Result.success(listTreeVos);
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody String[] ids) {
        List<String> categoryIds = Arrays.asList(ids);
        productCategoryService.deleteByIds(categoryIds);
        return Result.success();
    }

    @PostMapping("/save")
    public Result<?> save(@Valid @RequestBody ProductCategorySaveDTO saveDto) {
        ProductCategory data = new ProductCategory();
        BeanUtil.copyProperties(saveDto, data);
        productCategoryService.save(data);
        return Result.success();
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        ProductCategory data = productCategoryService.getById(id);
        return Result.success(data);
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductCategoryUpdateDTO saveDto) {
        ProductCategory data = new ProductCategory();
        BeanUtil.copyProperties(saveDto, data);
        productCategoryService.updateData(data);
        return Result.success();
    }

}
