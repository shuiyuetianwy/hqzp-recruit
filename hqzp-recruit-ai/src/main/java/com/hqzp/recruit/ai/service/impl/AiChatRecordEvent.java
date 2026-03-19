package com.hqzp.recruit.ai.service.impl;

import com.hqzp.recruit.common.domain.entity.AiChatRecord;
import org.springframework.context.ApplicationEvent;

/**
 * Published after every AI call so the startup module can persist the record
 * without creating a dependency from ai → startup.
 */
public class AiChatRecordEvent extends ApplicationEvent {

    private final AiChatRecord record;

    public AiChatRecordEvent(Object source, AiChatRecord record) {
        super(source);
        this.record = record;
    }

    public AiChatRecord getRecord() {
        return record;
    }
}
