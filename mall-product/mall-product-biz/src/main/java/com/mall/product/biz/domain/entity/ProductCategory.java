package com.mall.product.biz.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class ProductCategory extends BaseEntity implements Serializable {
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
     * 逻辑删除（1：删除，0：未删除）
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer deleted;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 图片
     */
    private String img;

}
