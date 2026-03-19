package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * File upload record — tracks every file stored in S3.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_file")
public class SysFile extends BaseEntity {

    /** S3 object key. */
    private String fileKey;

    /** Original filename. */
    private String originalName;

    /** MIME type. */
    private String contentType;

    /** File size in bytes. */
    private Long fileSize;

    /** S3 bucket name. */
    private String bucket;

    /** Public access URL (if bucket is public). */
    private String url;

    /**
     * Business type: avatar/resume/company_logo/other.
     */
    private String bizType;

    /** Associated business record ID. */
    private Long bizId;
}
