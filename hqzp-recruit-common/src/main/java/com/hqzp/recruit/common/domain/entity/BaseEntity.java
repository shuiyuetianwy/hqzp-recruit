package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base entity with audit fields for all MyBatis-Plus entities.
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** Creator user ID, auto-filled by MetaObjectHandler. */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /** Updater user ID, auto-filled by MetaObjectHandler. */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** Logical delete flag: 0=normal, 1=deleted. */
    @TableLogic
    private Integer deleted;
}
