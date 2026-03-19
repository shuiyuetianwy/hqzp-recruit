package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Candidate resume.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("resume")
public class Resume extends BaseEntity {

    private Long userId;

    private String realName;

    /** Gender: 0=unknown, 1=male, 2=female. */
    private Integer gender;

    private Integer age;

    private String phone;

    private String email;

    private String city;

    private String avatar;

    /** Current job title. */
    private String currentTitle;

    /** Years of experience. */
    private Integer experienceYears;

    /** Highest education: 大专/本科/硕士/博士. */
    private String education;

    /** Self introduction / summary. */
    private String summary;

    /** AI-generated resume score (0-100). */
    private Integer aiScore;

    /** AI analysis result (JSON). */
    private String aiAnalysis;

    /** Attachment file key in S3. */
    private String attachmentKey;

    /** Attachment original filename. */
    private String attachmentName;

    /** Visibility: 0=private, 1=public. */
    private Integer visibility;
}
