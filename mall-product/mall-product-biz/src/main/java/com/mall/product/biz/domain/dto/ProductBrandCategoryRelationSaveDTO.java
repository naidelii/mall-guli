package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Data
public class ProductBrandCategoryRelationSaveDTO {

    /**
     * 品牌id
     */
    @NotBlank(message = "品牌id不能为空")
    private String brandId;

    /**
     * 分类id
     */
    @NotBlank(message = "分类id不能为空")
    private String categoryId;

}
