package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Work experience entry on a resume.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("resume_work_exp")
public class ResumeWorkExp extends BaseEntity {

    private Long resumeId;

    private String companyName;

    private String jobTitle;

    private LocalDate startDate;

    private LocalDate endDate;

    /** Whether this is the current job. */
    private Boolean isCurrent;

    private String description;
}
