package com.hqzp.recruit.ai.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Structured result returned from AI service methods.
 */
@Data
@Builder
public class AiResult {

    /** Raw text content from the model. */
    private String content;

    /** Prompt tokens consumed. */
    private Integer promptTokens;

    /** Completion tokens consumed. */
    private Integer completionTokens;

    /** Total tokens consumed. */
    private Integer totalTokens;

    /** Wall-clock time for the API call in milliseconds. */
    private Long elapsedMs;

    /** Model used. */
    private String model;
}
