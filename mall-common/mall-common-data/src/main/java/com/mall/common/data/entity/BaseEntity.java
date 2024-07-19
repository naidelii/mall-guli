package com.mall.common.data.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.common.data.constant.DataConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author naidelii
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    @TableField(value = DataConstants.CREATE_BY, fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 创建时间
     */

    @TableField(value = DataConstants.CREATE_TIME, fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = DataConstants.UPDATE_BY, fill = FieldFill.UPDATE)
    protected String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = DataConstants.UPDATE_TIME, fill = FieldFill.UPDATE)
    protected LocalDateTime updateTime;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
