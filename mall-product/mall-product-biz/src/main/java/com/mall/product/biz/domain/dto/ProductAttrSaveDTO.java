package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author naidelii
 */
@Data
public class ProductAttrSaveDTO {

    /**
     * 属性名
     */
    @NotBlank(message = "属性名不能为空")
    private String attrName;

    /**
     * 所属分类id
     */
    @NotBlank(message = "所属分类不能为空")
    private String categoryId;

    /**
     * 所属属性分组id
     */
    private String attrGroupId;

    /**
     * 属性类型[0-销售属性，1-基本属性
     */
    @NotNull(message = "属性类型不能为空")
    private Integer attrType;

    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer isSearch;

    /**
     * 是否快速展示[0-否，1-是]展示在介绍上；，在sku中仍然可以调整
     */
    private Integer isQuickShow;

    /**
     * 启用状态[0-禁用，1-启用]
     */
    private Integer isEnable;

    /**
     * 值类型[0-单个值，1-可以选择多个值]
     */
    private Integer valueType;

    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;

    /**
     * 图标
     */
    private String icon;

}
