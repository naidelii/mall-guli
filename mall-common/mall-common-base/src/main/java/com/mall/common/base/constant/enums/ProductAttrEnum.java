package com.mall.common.base.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */
@Getter
@AllArgsConstructor
public enum ProductAttrEnum {

    /**
     * 基本属性
     */
    BASE(1, "基本属性"),

    /**
     * 销售属性
     */
    SALE(0, "销售属性");


    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

}
