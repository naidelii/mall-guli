package com.mall.product.biz.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.mall.product.biz.domain.entity.ProductAttributes;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性表
 *
 * @author naidelii
 */
@Data
public class ProductAttrRelationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 值类型[0-单个值，1-可以选择多个值]
     */
    private Integer valueType;

    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;


    public ProductAttrRelationVO(ProductAttributes data) {
        BeanUtil.copyProperties(data, this);
    }

}
