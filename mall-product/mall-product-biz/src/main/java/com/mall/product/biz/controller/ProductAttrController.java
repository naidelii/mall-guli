package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.constant.enums.ProductAttrEnum;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.dto.ProductAttrSaveDTO;
import com.mall.product.biz.domain.dto.ProductAttrUpdateDTO;
import com.mall.product.biz.domain.entity.ProductAttributes;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrVO;
import com.mall.product.biz.service.IProductAttributesService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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

    private final IProductAttributesService productAttrService;

    @GetMapping("/base/listPage")
    public Result<IPage<ProductAttrListVO>> listBasePage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                         @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                         ProductAttrQuery query) {
        IPage<ProductAttrListVO> pageList = productAttrService.listAttrWithPage(pageNo, pageSize, query, ProductAttrEnum.BASE.getType());
        return Result.success(pageList);
    }

    @GetMapping("/sale/listPage")
    public Result<IPage<ProductAttrListVO>> listSalePage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                         @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                         ProductAttrQuery query) {
        IPage<ProductAttrListVO> pageList = productAttrService.listAttrWithPage(pageNo, pageSize, query, ProductAttrEnum.SALE.getType());
        return Result.success(pageList);
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        ProductAttrVO vo = productAttrService.getDetailsById(id);
        return Result.success(vo);
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductAttrUpdateDTO updateDto) {
        ProductAttributes data = new ProductAttributes();
        BeanUtil.copyProperties(updateDto, data);
        productAttrService.updateData(data);
        return Result.success();
    }

    @PostMapping("/save")
    public Result<?> save(@Valid @RequestBody ProductAttrSaveDTO saveDto) {
        ProductAttributes data = new ProductAttributes();
        BeanUtil.copyProperties(saveDto, data);
        productAttrService.saveData(data);
        return Result.success();
    }


}
