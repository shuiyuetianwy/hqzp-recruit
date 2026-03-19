package com.hqzp.recruit.ai.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Response body from DeepSeek /v1/chat/completions.
 */
@Data
public class ChatResponse {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choice> choices;

    private Usage usage;

    @Data
    public static class Choice {
        private Integer index;
        private ChatMessage message;

        @JSONField(name = "finish_reason")
        private String finishReason;
    }

    @Data
    public static class Usage {
        @JSONField(name = "prompt_tokens")
        private Integer promptTokens;

        @JSONField(name = "completion_tokens")
        private Integer completionTokens;

        @JSONField(name = "total_tokens")
        private Integer totalTokens;
    }

    /** Convenience: extract the first choice's content. */
    public String firstContent() {
        if (choices == null || choices.isEmpty()) return "";
        ChatMessage msg = choices.get(0).getMessage();
        return msg == null ? "" : msg.getContent();
    }
}
