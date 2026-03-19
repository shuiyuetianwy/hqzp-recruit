package com.hqzp.recruit.file.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Result returned after a successful file upload.
 */
@Data
@Builder
public class UploadResult {

    /** S3 object key. */
    private String fileKey;

    /** Original filename. */
    private String originalName;

    /** MIME type. */
    private String contentType;

    /** File size in bytes. */
    private Long fileSize;

    /** Public access URL (if bucket is public or CDN-fronted). */
    private String url;

    /** Pre-signed URL for private buckets (expires after configured TTL). */
    private String presignedUrl;

    /** Persisted SysFile record ID. */
    private Long fileId;
}
