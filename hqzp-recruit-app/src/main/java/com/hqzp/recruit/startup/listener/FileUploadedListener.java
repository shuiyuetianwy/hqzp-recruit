package com.hqzp.recruit.startup.listener;

import com.hqzp.recruit.common.domain.entity.SysFile;
import com.hqzp.recruit.file.dto.UploadResult;
import com.hqzp.recruit.file.service.impl.FileUploadedEvent;
import com.hqzp.recruit.startup.mapper.SysFileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Persists a {@code SysFile} record after every successful S3 upload.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadedListener {

    private final SysFileMapper sysFileMapper;

    @Async
    @EventListener
    public void onFileUploaded(FileUploadedEvent event) {
        try {
            UploadResult r = event.getResult();
            SysFile file = new SysFile();
            file.setFileKey(r.getFileKey());
            file.setOriginalName(r.getOriginalName());
            file.setContentType(r.getContentType());
            file.setFileSize(r.getFileSize());
            file.setBucket(null); // set by S3Properties if needed
            file.setUrl(r.getUrl());
            file.setBizType(event.getBizType());
            file.setBizId(event.getBizId());
            sysFileMapper.insert(file);
            // Write back the generated ID to the result for callers that need it
            r.setFileId(file.getId());
        } catch (Exception e) {
            log.warn("Failed to persist SysFile record: {}", e.getMessage());
        }
    }
}
