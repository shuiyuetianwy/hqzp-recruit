package com.hqzp.recruit.file.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.file.config.FileProperties;
import com.hqzp.recruit.file.config.S3Properties;
import com.hqzp.recruit.file.dto.UploadResult;
import com.hqzp.recruit.file.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final AmazonS3 amazonS3;
    private final S3Properties s3Props;
    private final FileProperties fileProps;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UploadResult upload(MultipartFile file, String bizType, Long bizId) {
        validateFile(file);
        try {
            return upload(file.getInputStream(), file.getOriginalFilename(),
                    file.getContentType(), file.getSize(), bizType, bizId);
        } catch (IOException e) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "读取文件失败: " + e.getMessage());
        }
    }

    @Override
    public UploadResult upload(InputStream inputStream, String originalName,
                               String contentType, long size, String bizType, Long bizId) {
        String fileKey = buildFileKey(bizType, originalName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(size);

        try {
            PutObjectRequest putRequest = new PutObjectRequest(
                    s3Props.getBucket(), fileKey, inputStream, metadata);
            amazonS3.putObject(putRequest);
            log.info("Uploaded file: key={} size={} bizType={}", fileKey, size, bizType);
        } catch (Exception e) {
            log.error("S3 upload failed: key={}", fileKey, e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "上传失败: " + e.getMessage());
        }

        String publicUrl = buildPublicUrl(fileKey);
        String presignedUrl = generatePresignedUrl(fileKey);

        UploadResult result = UploadResult.builder()
                .fileKey(fileKey)
                .originalName(originalName)
                .contentType(contentType)
                .fileSize(size)
                .url(publicUrl)
                .presignedUrl(presignedUrl)
                .build();

        // Publish event so startup module can persist SysFile record
        eventPublisher.publishEvent(new FileUploadedEvent(this, result, bizType, bizId));

        return result;
    }

    @Override
    public String generatePresignedUrl(String fileKey) {
        try {
            Date expiry = new Date(System.currentTimeMillis()
                    + (long) s3Props.getPresignedExpiryMinutes() * 60 * 1000);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
                    s3Props.getBucket(), fileKey)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiry);
            URL url = amazonS3.generatePresignedUrl(request);
            return url.toString();
        } catch (Exception e) {
            log.warn("Failed to generate presigned URL for key={}: {}", fileKey, e.getMessage());
            return buildPublicUrl(fileKey);
        }
    }

    @Override
    public void delete(String fileKey) {
        try {
            amazonS3.deleteObject(s3Props.getBucket(), fileKey);
            log.info("Deleted S3 object: key={}", fileKey);
        } catch (Exception e) {
            log.error("Failed to delete S3 object: key={}", fileKey, e);
            throw new BusinessException(ResultCode.FILE_NOT_FOUND, "文件删除失败: " + e.getMessage());
        }
    }

    @Override
    public boolean exists(String fileKey) {
        try {
            amazonS3.getObjectMetadata(s3Props.getBucket(), fileKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "文件不能为空");
        }
        if (file.getSize() > fileProps.maxSizeBytes()) {
            throw new BusinessException(ResultCode.FILE_SIZE_EXCEEDED,
                    "文件大小不能超过 " + fileProps.getMaxSizeMb() + "MB");
        }
        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || !fileProps.allowedTypeList().contains(contentType)) {
            throw new BusinessException(ResultCode.FILE_TYPE_NOT_ALLOWED,
                    "不支持的文件类型: " + contentType);
        }
    }

    /**
     * Builds a unique S3 key: {bizType}/{yyyy}/{MM}/{dd}/{uuid}.{ext}
     */
    private String buildFileKey(String bizType, String originalName) {
        String ext = "";
        if (StringUtils.hasText(originalName) && originalName.contains(".")) {
            ext = "." + originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
        }
        LocalDate today = LocalDate.now();
        return String.format("%s/%d/%02d/%02d/%s%s",
                bizType, today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                UUID.randomUUID().toString().replace("-", ""), ext);
    }

    private String buildPublicUrl(String fileKey) {
        String base = s3Props.getPublicBaseUrl();
        if (!StringUtils.hasText(base)) return "";
        return base.endsWith("/") ? base + fileKey : base + "/" + fileKey;
    }
}
