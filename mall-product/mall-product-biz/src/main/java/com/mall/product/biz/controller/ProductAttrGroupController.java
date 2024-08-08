package com.mall.product.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.dto.*;
import com.mall.product.biz.domain.entity.ProductAttributeGroups;
import com.mall.product.biz.domain.entity.ProductAttributes;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupWithAttrsVO;
import com.mall.product.biz.domain.vo.ProductAttrRelationVO;
import com.mall.product.biz.service.IProductAttributeGroupsService;
import com.mall.product.biz.service.IProductAttributesService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final IProductAttributeGroupsService attrGroupService;
    private final IProductAttributesService attrService;

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

    @GetMapping("/listAttrGroupWithAttrs")
    public Result<?> listAttrGroupWithAttrs(@RequestParam("categoryId") String categoryId) {
        List<ProductAttrGroupWithAttrsVO> list = attrGroupService.listAttrGroupWithAttrsByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/listByCategoryId")
    public Result<?> listByCategoryId(ProductAttrGroupQuery query) {
        List<ProductAttrGroupListVO> list = attrGroupService.listAttrGroupByCategoryId(query.getCategoryId());
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody ProductAttrGroupSaveDTO saveDto) {
        ProductAttributeGroups data = new ProductAttributeGroups();
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
        List<ProductAttributes> updateAttributes = new ArrayList<>();
        for (String attrId : saveDto.getAttrIds()) {
            ProductAttributes productAttributes = new ProductAttributes();
            productAttributes.setId(attrId);
            productAttributes.setAttrGroupId(attrGroupId);
            updateAttributes.add(productAttributes);
        }
        attrService.updateBatchById(updateAttributes);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody ProductAttrGroupUpdateDTO updateDto) {
        ProductAttributeGroups data = new ProductAttributeGroups();
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
        ProductAttributes productAttributes = new ProductAttributes();
        productAttributes.setId(dto.getAttrId());
        productAttributes.setAttrGroupId(StringUtils.EMPTY);
        attrService.updateById(productAttributes);
        return Result.success();
    }


}
