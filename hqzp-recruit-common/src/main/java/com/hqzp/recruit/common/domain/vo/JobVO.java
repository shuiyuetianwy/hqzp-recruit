package com.hqzp.recruit.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class JobVO {

    private Long id;

    private Long companyId;

    private String companyName;

    private String companyLogo;

    private String companyIndustry;

    private Integer companyScale;

    private String title;

    private String description;

    private String requirement;

    private String city;

    private String address;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String experience;

    private String education;

    private String category;

    private Integer headcount;

    private Integer jobType;

    private Integer status;

    private Integer viewCount;

    private Integer deliveryCount;

    private LocalDateTime createTime;

    /** Whether the current user has applied. */
    private Boolean applied;
}
