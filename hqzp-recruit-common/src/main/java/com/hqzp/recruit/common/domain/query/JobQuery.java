package com.hqzp.recruit.common.domain.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Job search / filter parameters.
 */
@Data
public class JobQuery extends PageQuery {

    private String keyword;

    private String city;

    private String category;

    private String experience;

    private String education;

    private Integer jobType;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private Long companyId;

    private Integer status;
}
