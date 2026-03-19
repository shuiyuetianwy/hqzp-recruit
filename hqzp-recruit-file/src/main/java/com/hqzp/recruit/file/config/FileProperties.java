package com.hqzp.recruit.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * General file upload constraints, bound from {@code file.*} properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /** Comma-separated allowed MIME types. */
    private String allowedTypes =
            "image/jpeg,image/png,image/gif,image/webp," +
            "application/pdf,application/msword," +
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    /** Maximum upload size in megabytes. */
    private int maxSizeMb = 20;

    public List<String> allowedTypeList() {
        return Arrays.asList(allowedTypes.split(","));
    }

    public long maxSizeBytes() {
        return (long) maxSizeMb * 1024 * 1024;
    }
}
