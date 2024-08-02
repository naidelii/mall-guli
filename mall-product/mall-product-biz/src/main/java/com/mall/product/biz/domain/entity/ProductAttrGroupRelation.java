package com.mall.product.biz.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性和商品属性分组关联表
 *
 * @author naidelii
 */
@Data
public class ProductAttrGroupRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 属性id
     */
    private String attrId;

    /**
     * 属性分组id
     */
    private String attrGroupId;

    /**
     * 排序值
     */
    private Integer sortOrder;

}
