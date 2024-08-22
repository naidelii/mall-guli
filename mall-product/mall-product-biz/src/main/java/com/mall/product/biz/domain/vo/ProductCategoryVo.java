package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父级分类id
     */
    private String parentId;

    /**
     * 父级分类名称
     */
    private String parentName;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 图片
     */
    private String imageUrl;

    public ProductCategoryVo(ProductCategory data) {
        BeanUtil.copyProperties(data, this);
    }

}
