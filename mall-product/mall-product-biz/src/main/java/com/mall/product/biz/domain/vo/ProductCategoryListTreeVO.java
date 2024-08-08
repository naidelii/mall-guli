package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mall.product.biz.domain.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryListTreeVO implements Serializable {
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
     * 层级
     */
    private Integer level;

    /**
     * 是否显示（0-不显示，1-显示）
     */
    private Integer isShow;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 图片
     */
    private String imageUrl;

    /**
     * 子分类
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProductCategoryListTreeVO> children;

    public ProductCategoryListTreeVO(ProductCategory entity) {
        BeanUtil.copyProperties(entity, this);
    }

}
