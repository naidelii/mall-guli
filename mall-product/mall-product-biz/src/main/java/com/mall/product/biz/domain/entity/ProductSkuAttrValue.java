package com.mall.product.biz.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sku销售属性值
 *
 * @author naidelii
 */
@Data
public class ProductSkuAttrValue implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * sku_id
     */
    private String skuId;

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

}
