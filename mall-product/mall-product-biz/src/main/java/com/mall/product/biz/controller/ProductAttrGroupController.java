package com.mall.product.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.service.IProductAttrGroupService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

}
