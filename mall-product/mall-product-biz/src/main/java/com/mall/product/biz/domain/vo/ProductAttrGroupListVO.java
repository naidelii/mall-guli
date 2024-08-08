package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductAttributeGroups;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性分组
 *
 * @author naidelii
 */
@Data
public class ProductAttrGroupListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 所属分类id
     */
    private String categoryId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 介绍
     */
    private String description;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序值
     */
    private Integer sortOrder;

    public ProductAttrGroupListVO(ProductAttributeGroups data) {
        BeanUtil.copyProperties(data, this);
    }

}
