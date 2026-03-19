package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Job application (delivery record).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_application")
public class JobApplication extends BaseEntity {

    private Long jobId;

    private Long candidateId;

    private Long resumeId;

    private Long companyId;

    /**
     * Status:
     * 0=待查看, 1=已查看, 2=邀请面试, 3=面试中, 4=已录用, 5=已拒绝, 6=已放弃.
     */
    private Integer status;

    /** HR remark / notes. */
    private String hrRemark;

    /** AI match score (0-100). */
    private Integer aiMatchScore;

    /** AI match analysis (JSON). */
    private String aiMatchAnalysis;

    /** Time HR viewed the application. */
    private LocalDateTime viewTime;

    /** Interview scheduled time. */
    private LocalDateTime interviewTime;

    /** Interview location or meeting link. */
    private String interviewLocation;
}
