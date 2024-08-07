package com.mall.member.biz.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author naidelii
 */
@Data
public class MemberLevelSaveDTO {

    /**
     * 等级名称
     */
    @NotBlank(message = "等级名称不能为空")
    private String name;

    /**
     * 等级需要的成长值
     */
    private Integer growthPoint;

    /**
     * 是否为默认等级（0：不是，1：是）
     */
    private Integer isDefaultLevel;

    /**
     * 免运费标准
     */
    private BigDecimal freeShippingThreshold;

    /**
     * 每次评价获取的成长值
     */
    private Integer commentGrowthPoint;

    /**
     * 是否有免邮特权（0：不是，1：是）
     */
    private Integer isFreeShipping;

    /**
     * 是否有会员价格特权（0：不是，1：是）
     */
    private Integer isMemberPrice;

    /**
     * 是否有生日特权（0：不是，1：是）
     */
    private Integer isBirthdayPrivilege;

    /**
     * 备注
     */
    private String remark;
}
