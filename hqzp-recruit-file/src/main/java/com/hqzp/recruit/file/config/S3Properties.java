package com.hqzp.recruit.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * S3-compatible storage configuration, bound from {@code file.s3.*} properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.s3")
public class S3Properties {

    /** S3 endpoint URL (AWS or compatible, e.g. MinIO). */
    private String endpoint = "https://s3.amazonaws.com";

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String region = "us-east-1";

    /** Base URL for public object access. */
    private String publicBaseUrl;

    /** Pre-signed URL expiry in minutes. */
    private int presignedExpiryMinutes = 60;
}
