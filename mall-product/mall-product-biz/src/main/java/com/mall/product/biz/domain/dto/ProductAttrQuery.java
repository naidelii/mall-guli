package com.mall.product.biz.domain.dto;

import lombok.Data;

/**
 * 商品属性表
 *
 * @author naidelii
 */
@Data
public class ProductAttrQuery {

    /**
     * 所属分类id
     */
    private String categoryId;

    /**
     * 关键字
     */
    private String key;

}
