package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Company profile.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("company")
public class Company extends BaseEntity {

    private String name;

    private String logo;

    private String description;

    /** Industry category. */
    private String industry;

    /** Company scale: 1=<20, 2=20-99, 3=100-499, 4=500-999, 5=1000+. */
    private Integer scale;

    /** Financing stage: 未融资/天使轮/A轮/B轮/C轮/上市/不需要融资. */
    private String financingStage;

    private String city;

    private String address;

    private String website;

    /** Verification status: 0=pending, 1=verified, 2=rejected. */
    private Integer verifyStatus;
}
