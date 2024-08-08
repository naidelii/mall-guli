package com.mall.product.biz.domain.dto;

import lombok.Data;

/**
 * 品牌表
 *
 * @author naidelii
 */
@Data
public class ProductBrandQuery {

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 检索首字母
     */
    private String firstLetter;

}
