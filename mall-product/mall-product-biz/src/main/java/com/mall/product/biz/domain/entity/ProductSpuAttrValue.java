package com.mall.product.biz.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * spu属性值
 *
 * @author naidelii
 */
@Data
public class ProductSpuAttrValue implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 商品id
     */
    private String spuId;

    /**
     * 属性id
     */
    private String attrId;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 是否快速显示（0：否，1：是）
     */
    private Integer isQuickShow;

}
