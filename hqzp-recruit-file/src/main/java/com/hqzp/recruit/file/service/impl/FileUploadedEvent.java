package com.hqzp.recruit.file.service.impl;

import com.hqzp.recruit.file.dto.UploadResult;
import org.springframework.context.ApplicationEvent;

/**
 * Published after a successful S3 upload so the startup module can persist
 * a {@code SysFile} record without creating a file → startup dependency.
 */
public class FileUploadedEvent extends ApplicationEvent {

    private final UploadResult result;
    private final String bizType;
    private final Long bizId;

    public FileUploadedEvent(Object source, UploadResult result, String bizType, Long bizId) {
        super(source);
        this.result = result;
        this.bizType = bizType;
        this.bizId = bizId;
    }

    public UploadResult getResult() { return result; }
    public String getBizType()      { return bizType; }
    public Long getBizId()          { return bizId; }
}
