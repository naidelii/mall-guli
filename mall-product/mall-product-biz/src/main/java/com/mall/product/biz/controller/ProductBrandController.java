package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.dto.ProductBrandQuery;
import com.mall.product.biz.domain.dto.ProductBrandSaveDTO;
import com.mall.product.biz.domain.dto.ProductBrandUpdateDTO;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.domain.vo.ProductBrandListVO;
import com.mall.product.biz.service.IProductBrandService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 品牌表
 *
 * @author naidelii
 */
@Api(tags = "品牌表")
@Slf4j
@RequestMapping("/product/brand")
@RestController
@RequiredArgsConstructor
public class ProductBrandController {

    private final IProductBrandService productBrandService;

    @GetMapping("/listPage")
    public Result<IPage<ProductBrandListVO>> listPage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                      @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                      ProductBrandQuery query) {
        IPage<ProductBrandListVO> pageList = productBrandService.selectListPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }

    @GetMapping("/listByCategoryId")
    public Result<List<ProductBrandListVO>> listByCategoryId(@RequestParam("categoryId") String categoryId) {
        List<ProductBrand> list = productBrandService.listByCategoryId(categoryId);
        List<ProductBrandListVO> voList = list.stream()
                .map(ProductBrandListVO::new)
                .collect(Collectors.toList());
        return Result.success(voList);
    }

    @PostMapping("/save")
    public Result<?> save(@Valid @RequestBody ProductBrandSaveDTO saveDto) {
        ProductBrand data = new ProductBrand();
        BeanUtil.copyProperties(saveDto, data);
        productBrandService.save(data);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductBrandUpdateDTO updateDto) {
        ProductBrand data = new ProductBrand();
        BeanUtil.copyProperties(updateDto, data);
        productBrandService.updateData(data);
        return Result.success();
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        ProductBrand data = productBrandService.getById(id);
        return Result.success(data);
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody String[] ids) {
        List<String> removeIds = Arrays.asList(ids);
        productBrandService.deleteByIds(removeIds);
        return Result.success();
    }

}
