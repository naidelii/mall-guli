package com.mall.product.biz.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.common.base.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("product_category")
public class ProductCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String icon;

}
