package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Job posting.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job")
public class Job extends BaseEntity {

    private Long companyId;

    /** HR user who published this job. */
    private Long publisherId;

    private String title;

    private String description;

    private String requirement;

    private String city;

    private String address;

    /** Monthly salary lower bound (unit: 元). */
    private BigDecimal salaryMin;

    /** Monthly salary upper bound (unit: 元). */
    private BigDecimal salaryMax;

    /** Experience requirement: 不限/1年以下/1-3年/3-5年/5-10年/10年以上. */
    private String experience;

    /** Education requirement: 不限/大专/本科/硕士/博士. */
    private String education;

    /** Job category / function. */
    private String category;

    /** Headcount to recruit. */
    private Integer headcount;

    /** Job type: 1=全职, 2=兼职, 3=实习. */
    private Integer jobType;

    /** Status: 0=draft, 1=published, 2=closed. */
    private Integer status;

    /** View count. */
    private Integer viewCount;

    /** Delivery count. */
    private Integer deliveryCount;
}
