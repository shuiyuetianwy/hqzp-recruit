package com.hqzp.recruit.common.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class JobDTO {

    private Long id;

    @NotBlank(message = "职位名称不能为空")
    private String title;

    @NotBlank(message = "职位描述不能为空")
    private String description;

    private String requirement;

    @NotBlank(message = "工作城市不能为空")
    private String city;

    private String address;

    @NotNull(message = "薪资下限不能为空")
    private BigDecimal salaryMin;

    @NotNull(message = "薪资上限不能为空")
    private BigDecimal salaryMax;

    private String experience;

    private String education;

    private String category;

    private Integer headcount;

    private Integer jobType;
}
