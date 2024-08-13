package com.mall.product.biz.domain.entity;

import com.mall.common.base.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * spu信息
 *
 * @author naidelii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSpuInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 所属分类id
     */
    private String categoryId;

    /**
     * 所属品牌id
     */
    private String brandId;

    /**
     * 商品简短描述
     */
    private String shortDescription;

    /**
     * 上架状态（0：下架，1：上架）
     */
    private Integer publishStatus;

    /**
     * 商品详细介绍（富文本）
     */
    private String detailedDescription;

}
