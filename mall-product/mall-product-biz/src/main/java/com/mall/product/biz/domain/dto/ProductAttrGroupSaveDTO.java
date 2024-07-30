package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品属性分组
 *
 * @author naidelii
 */
@Data
public class ProductAttrGroupSaveDTO {

    /**
     * 所属分类id
     */
    @NotBlank(message = "所属分类不能为空")
    private String categoryId;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空")
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
