package com.mall.product.biz.domain.entity;

import com.mall.common.base.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sku信息
 *
 * @author naidelii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSkuInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 产品id
     */
    private String spuId;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 默认图片
     */
    private String defaultImageUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 销量
     */
    private Long salesCount;

}
