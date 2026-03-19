package com.hqzp.recruit.common.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationVO {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private Long companyId;

    private String companyName;

    private String companyLogo;

    private Long candidateId;

    private String candidateName;

    private String candidatePhone;

    private Long resumeId;

    private Integer status;

    private String statusLabel;

    private String hrRemark;

    private Integer aiMatchScore;

    private String aiMatchAnalysis;

    private LocalDateTime createTime;

    private LocalDateTime viewTime;

    private LocalDateTime interviewTime;

    private String interviewLocation;
}
