package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.domain.entity.ProductAttrGroup;
import lombok.Data;

import java.util.List;

/**
 * @author naidelii
 */
@Data
public class ProductAttrGroupWithAttrsVO {
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

    /**
     * 分组下所有属性
     */
    private List<ProductAttr> attrList;

    public ProductAttrGroupWithAttrsVO(ProductAttrGroup data) {
        BeanUtil.copyProperties(data, this);
    }
}
