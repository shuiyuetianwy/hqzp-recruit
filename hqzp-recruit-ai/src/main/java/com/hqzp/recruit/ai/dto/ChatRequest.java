package com.hqzp.recruit.ai.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Request body for DeepSeek /v1/chat/completions.
 */
@Data
@Builder
public class ChatRequest {

    private String model;

    private List<ChatMessage> messages;

    @JSONField(name = "max_tokens")
    private Integer maxTokens;

    private Double temperature;

    /** Whether to stream the response. Always false for this integration. */
    private Boolean stream;
}
