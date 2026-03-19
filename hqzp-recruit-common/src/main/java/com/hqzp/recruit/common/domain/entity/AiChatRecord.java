package com.hqzp.recruit.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AI conversation record for DeepSeek interactions.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_chat_record")
public class AiChatRecord extends BaseEntity {

    private Long userId;

    /**
     * Scene: resume_analysis / job_match / interview_question / career_advice.
     */
    private String scene;

    /** Associated business ID (resumeId, jobId, applicationId, etc.). */
    private Long bizId;

    /** User prompt sent to DeepSeek. */
    private String prompt;

    /** Full response from DeepSeek. */
    private String response;

    /** Model used, e.g. deepseek-chat. */
    private String model;

    /** Prompt tokens consumed. */
    private Integer promptTokens;

    /** Completion tokens consumed. */
    private Integer completionTokens;

    /** Total tokens consumed. */
    private Integer totalTokens;

    /** Elapsed time in milliseconds. */
    private Long elapsedMs;

    /** Status: 0=failed, 1=success. */
    private Integer status;

    private String errorMessage;
}
