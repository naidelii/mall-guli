package com.mall.product.biz.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Data
public class ProductBrandCategoryRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 分类名称
     */
    private String categoryName;

}
