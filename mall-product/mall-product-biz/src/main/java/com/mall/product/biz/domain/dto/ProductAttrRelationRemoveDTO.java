package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author naidelii
 */
@Data
public class ProductAttrRelationRemoveDTO {

    /**
     * 属性id
     */
    @NotBlank(message = "属性id不能为空")
    private String attrId;

    /**
     * 属性分组id
     */
    @NotBlank(message = "属性分组id不能为空")
    private String attrGroupId;


}
