package com.mall.member.biz.entity;

import com.mall.common.base.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员等级
 *
 * @author naidelii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MemberLevel extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 等级名称
     */
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
