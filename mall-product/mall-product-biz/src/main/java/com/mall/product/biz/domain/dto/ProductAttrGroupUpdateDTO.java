package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品属性分组
 *
 * @author naidelii
 */
@Data
public class ProductAttrGroupUpdateDTO {

    /**
     * 主键
     */
    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 所属分类id
     */
    private String categoryId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 介绍
     */
    private String description;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序值
     */
    private Integer sortOrder;

}
