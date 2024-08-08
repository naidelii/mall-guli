package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductBrand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 品牌表
 *
 * @author naidelii
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrandListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌logo
     */
    private String logoUrl;

    /**
     * 介绍
     */
    private String description;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 是否显示（0：不显示，1：显示）
     */
    private Integer isShow;

    /**
     * 检索首字母
     */
    private String firstLetter;

    public ProductBrandListVO(ProductBrand entity) {
        BeanUtil.copyProperties(entity, this);
    }

}
