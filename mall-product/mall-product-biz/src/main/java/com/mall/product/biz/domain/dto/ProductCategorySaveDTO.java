package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
@Data
public class ProductCategorySaveDTO {
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    /**
     * 父级分类id
     */
    @NotBlank(message = "父级分类id不能为空")
    private String parentId;

    /**
     * 层级
     */
    @NotNull(message = "层级不能为空")
    private Integer level;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 图片
     */
    private String imageUrl;

}
