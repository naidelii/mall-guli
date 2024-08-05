package com.mall.product.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author naidelii
 */
@Data
public class ProductAttrRelationSaveDTO {

    /**
     * 属性id集合
     */
    @NotEmpty(message = "属性不能为空")
    private List<String> attrIds;

    /**
     * 属性分组id
     */
    @NotBlank(message = "属性分组id不能为空")
    private String attrGroupId;


}
