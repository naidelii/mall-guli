package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.dto.ProductAttrUpdateDTO;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrVO;
import com.mall.product.biz.service.IProductAttrService;
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

    private final IProductAttrService productAttrService;

    @GetMapping("/listPage")
    public Result<IPage<ProductAttrListVO>> listPage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                     @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                     ProductAttrQuery query) {
        IPage<ProductAttrListVO> pageList = productAttrService.listAttrWithPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        ProductAttrVO vo = productAttrService.getDetailsById(id);
        return Result.success(vo);
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductAttrUpdateDTO updateDto) {
        ProductAttr data = new ProductAttr();
        BeanUtil.copyProperties(updateDto, data);
        // 所属分组id
        String attrGroupId = updateDto.getAttrGroupId();
        productAttrService.updateData(data, attrGroupId);
        return Result.success();
    }


}
