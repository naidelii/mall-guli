package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.dto.*;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.domain.vo.ProductAttrRelationVO;
import com.mall.product.biz.service.IProductAttrGroupRelationService;
import com.mall.product.biz.service.IProductAttrGroupService;
import com.mall.product.biz.service.IProductAttrService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    private final IProductAttrGroupService attrGroupService;
    private final IProductAttrService attrService;
    private final IProductAttrGroupRelationService attrGroupRelationService;

    @GetMapping("/listPage")
    public Result<IPage<ProductAttrGroupListVO>> listPage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                          @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                          ProductAttrGroupQuery query) {
        IPage<ProductAttrGroupListVO> pageList = attrGroupService.listAttrGroupWithPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }

    @GetMapping("/listAttrGroupsWithAttributes")
    public Result<?> listAttrGroupsWithAttributes(@RequestParam("attrGroupId") String attrGroupId) {
        List<ProductAttrRelationVO> list = attrService.listAttrGroupsWithAttributes(attrGroupId);
        return Result.success(list);
    }

    @GetMapping("/listUnrelatedAttributes")
    public Result<?> listUnrelatedAttributes(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                             @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                             @RequestParam("attrGroupId") String attrGroupId,
                                             @RequestParam("categoryId") String categoryId) {
        IPage<ProductAttrRelationVO> pageList = attrService.listUnrelatedAttributes(pageNo, pageSize, attrGroupId, categoryId);
        return Result.success(pageList);
    }


    @GetMapping("/listByCategoryId")
    public Result<?> listByCategoryId(ProductAttrGroupQuery query) {
        List<ProductAttrGroupListVO> list = attrGroupService.listAttrGroupByCategoryId(query.getCategoryId());
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody ProductAttrGroupSaveDTO saveDto) {
        ProductAttrGroup data = new ProductAttrGroup();
        BeanUtil.copyProperties(saveDto, data);
        attrGroupService.save(data);
        return Result.success();
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        ProductAttrGroupVO vo = attrGroupService.getDetailsById(id);
        return Result.success(vo);
    }

    @PostMapping("/addAttributeToGroup")
    public Result<?> addAttributeToGroup(@Validated @RequestBody ProductAttrRelationSaveDTO saveDto) {
        String attrGroupId = saveDto.getAttrGroupId();
        List<ProductAttrGroupRelation> saveList = new ArrayList<>();
        for (String attrId : saveDto.getAttrIds()) {
            ProductAttrGroupRelation relation = new ProductAttrGroupRelation();
            relation.setAttrGroupId(attrGroupId);
            relation.setAttrId(attrId);
            saveList.add(relation);
        }
        attrGroupRelationService.saveBatch(saveList);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductAttrGroupUpdateDTO updateDto) {
        ProductAttrGroup data = new ProductAttrGroup();
        BeanUtil.copyProperties(updateDto, data);
        attrGroupService.updateById(data);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<?> delete(String id) {
        attrGroupService.removeById(id);
        return Result.success();
    }

    @PostMapping("/deleteAttrRelation")
    public Result<?> deleteAttrRelation(@Validated @RequestBody ProductAttrRelationRemoveDTO dto) {
        attrService.removeRelation(dto.getAttrId(), dto.getAttrGroupId());
        return Result.success();
    }


}
