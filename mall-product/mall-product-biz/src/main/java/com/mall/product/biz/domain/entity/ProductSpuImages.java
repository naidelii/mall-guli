package com.mall.product.biz.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * spu图片
 *
 * @author naidelii
 */
@Data
public class ProductSpuImages implements Serializable {
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
     * 图片名称
     */
    private String imageName;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 是否是默认图（0：否，1：是）
     */
    private Integer isDefaultImg;

}
