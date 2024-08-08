package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductBrandCategoryRelation;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Data
public class ProductBrandCategoryRelationListVO implements Serializable {
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
     * 分类名称
     */
    private String categoryName;

    public ProductBrandCategoryRelationListVO(ProductBrandCategoryRelation data) {
        BeanUtil.copyProperties(data, this);
    }

}
