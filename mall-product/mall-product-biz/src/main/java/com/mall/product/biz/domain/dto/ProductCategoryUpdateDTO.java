package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
@Data
public class ProductCategoryUpdateDTO {

    /**
     * 主键
     */
    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父级分类id
     */
    private String parentId;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 图片
     */
    private String imageUrl;

}
