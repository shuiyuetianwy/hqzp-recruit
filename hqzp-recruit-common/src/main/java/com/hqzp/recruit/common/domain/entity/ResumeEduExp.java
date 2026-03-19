package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Education experience entry on a resume.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("resume_edu_exp")
public class ResumeEduExp extends BaseEntity {

    private Long resumeId;

    private String schoolName;

    private String major;

    /** Degree: 大专/本科/硕士/博士. */
    private String degree;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;
}
