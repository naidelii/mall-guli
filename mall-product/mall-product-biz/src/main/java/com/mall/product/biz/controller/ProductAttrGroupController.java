package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.dto.ProductAttrGroupSaveDTO;
import com.mall.product.biz.domain.dto.ProductAttrGroupUpdateDTO;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.service.IProductAttrGroupService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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

    @GetMapping("/listPage")
    public Result<IPage<ProductAttrGroupListVO>> listPage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                          @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                          ProductAttrGroupQuery query) {
        IPage<ProductAttrGroupListVO> pageList = productAttrGroupService.listAttrGroupWithPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }


    @GetMapping("/listByCategoryId")
    public Result<?> listByCategoryId(ProductAttrGroupQuery query) {
        List<ProductAttrGroupListVO> list = productAttrGroupService.listAttrGroupByCategoryId(query.getCategoryId());
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody ProductAttrGroupSaveDTO saveDto) {
        ProductAttrGroup data = new ProductAttrGroup();
        BeanUtil.copyProperties(saveDto, data);
        productAttrGroupService.save(data);
        return Result.success();
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        ProductAttrGroupVO vo = productAttrGroupService.getDetailsById(id);
        return Result.success(vo);
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductAttrGroupUpdateDTO updateDto) {
        ProductAttrGroup data = new ProductAttrGroup();
        BeanUtil.copyProperties(updateDto, data);
        productAttrGroupService.updateById(data);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<?> delete(String id) {
        productAttrGroupService.removeById(id);
        return Result.success();
    }


}
