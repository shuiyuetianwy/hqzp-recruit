package com.hqzp.recruit.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A single message in a DeepSeek chat conversation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    /** Role: system / user / assistant. */
    private String role;

    private String content;

    public static ChatMessage system(String content) {
        return new ChatMessage("system", content);
    }

    public static ChatMessage user(String content) {
        return new ChatMessage("user", content);
    }

    public static ChatMessage assistant(String content) {
        return new ChatMessage("assistant", content);
    }
}
