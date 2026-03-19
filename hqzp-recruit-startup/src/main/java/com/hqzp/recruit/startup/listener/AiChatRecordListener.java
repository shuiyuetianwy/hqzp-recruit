package com.hqzp.recruit.startup.listener;

import com.hqzp.recruit.ai.service.impl.AiChatRecordEvent;
import com.hqzp.recruit.startup.mapper.AiChatRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Persists AI chat records asynchronously after each AI call.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AiChatRecordListener {

    private final AiChatRecordMapper aiChatRecordMapper;

    @Async
    @EventListener
    public void onAiChatRecord(AiChatRecordEvent event) {
        try {
            aiChatRecordMapper.insert(event.getRecord());
        } catch (Exception e) {
            log.warn("Failed to persist AI chat record: {}", e.getMessage());
        }
    }
}
