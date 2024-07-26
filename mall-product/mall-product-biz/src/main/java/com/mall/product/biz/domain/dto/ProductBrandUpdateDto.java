package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 品牌表
 *
 * @author naidelii
 */
@Data
public class ProductBrandUpdateDto {
    /**
     * 主键
     */
    @NotBlank(message = "id不能为空")
    private String id;


    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 介绍
     */
    private String description;

    /**
     * 是否显示（0：不显示，1：显示）
     */
    private Integer isShow;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 检索首字母
     */
    private String firstLetter;

}
