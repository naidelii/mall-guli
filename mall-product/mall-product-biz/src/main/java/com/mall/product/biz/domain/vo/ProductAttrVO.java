package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductAttributes;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性表
 *
 * @author naidelii
 */
@Data
public class ProductAttrVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 所属分类id
     */
    private String categoryId;

    /**
     * 属性分组od
     */
    private String attrGroupId;

    /**
     * 完整的所属分类路径
     */
    private String[] categoryPath;

    /**
     * 属性类型[0-销售属性，1-基本属性
     */
    private Integer attrType;

    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer isSearch;

    /**
     * 是否快速展示[0-否，1-是]展示在介绍上；，在sku中仍然可以调整
     */
    private Integer isShow;

    /**
     * 启用状态[0-禁用，1-启用]
     */
    private Integer enable;

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


    public ProductAttrVO(ProductAttributes data) {
        BeanUtil.copyProperties(data, this);
    }

}
