package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * System user — covers both recruiters (HR) and job seekers.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /** Login username / phone number. */
    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String phone;

    /**
     * User type: 1=admin, 2=hr, 3=candidate.
     */
    private Integer userType;

    /**
     * Status: 0=disabled, 1=enabled.
     */
    private Integer status;

    /** Company the HR belongs to (null for candidates). */
    private Long companyId;
}
