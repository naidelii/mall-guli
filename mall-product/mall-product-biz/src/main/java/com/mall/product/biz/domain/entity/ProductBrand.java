package com.mall.product.biz.domain.entity;

import com.mall.common.base.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 品牌表
 *
 * @author naidelii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductBrand extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
    private String describe;

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
