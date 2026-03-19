package com.hqzp.recruit.file.service;

import com.hqzp.recruit.file.dto.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService {

    /**
     * Upload a multipart file to S3.
     *
     * @param file     the uploaded file
     * @param bizType  business type prefix (avatar / resume / company_logo / other)
     * @param bizId    associated business record ID (nullable)
     */
    UploadResult upload(MultipartFile file, String bizType, Long bizId);

    /**
     * Upload raw bytes to S3.
     *
     * @param inputStream  data stream
     * @param originalName original filename
     * @param contentType  MIME type
     * @param size         content length in bytes
     * @param bizType      business type prefix
     * @param bizId        associated business record ID (nullable)
     */
    UploadResult upload(InputStream inputStream, String originalName,
                        String contentType, long size, String bizType, Long bizId);

    /**
     * Generate a pre-signed GET URL for a private object.
     *
     * @param fileKey  S3 object key
     * @return pre-signed URL valid for the configured TTL
     */
    String generatePresignedUrl(String fileKey);

    /**
     * Delete an object from S3.
     *
     * @param fileKey  S3 object key
     */
    void delete(String fileKey);

    /**
     * Check whether an object exists in S3.
     */
    boolean exists(String fileKey);
}
