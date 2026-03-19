package com.hqzp.recruit.ai.service.impl;

import com.hqzp.recruit.ai.dto.AiResult;
import com.hqzp.recruit.ai.service.AiRecruitService;
import com.hqzp.recruit.ai.service.AiService;
import com.hqzp.recruit.common.domain.entity.AiChatRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Delegates to {@link AiService} and publishes an {@link AiChatRecordEvent}
 * so the startup module can persist the record without a circular dependency.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiRecruitServiceImpl implements AiRecruitService {

    private final AiService aiService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public AiResult analyseResume(Long resumeId, String resumeText) {
        AiResult result = aiService.analyseResume(resumeText);
        publishRecord(null, "resume_analysis", resumeId, resumeText, result);
        return result;
    }

    @Override
    public AiResult matchJobResume(Long applicationId, String jobDescription, String resumeText) {
        AiResult result = aiService.matchJobResume(jobDescription, resumeText);
        publishRecord(null, "job_match", applicationId,
                "JD: " + jobDescription + "\nResume: " + resumeText, result);
        return result;
    }

    @Override
    public AiResult generateInterviewQuestions(Long applicationId, String jobDescription,
                                               String resumeText, int count) {
        AiResult result = aiService.generateInterviewQuestions(jobDescription, resumeText, count);
        publishRecord(null, "interview_question", applicationId,
                "JD: " + jobDescription + "\nResume: " + resumeText, result);
        return result;
    }

    @Override
    public AiResult careerAdvice(Long userId, String resumeText, String targetJob) {
        AiResult result = aiService.careerAdvice(resumeText, targetJob);
        publishRecord(userId, "career_advice", null,
                "Target: " + targetJob + "\nResume: " + resumeText, result);
        return result;
    }

    @Override
    public AiResult optimiseResumeSection(Long resumeId, String section, String content) {
        AiResult result = aiService.optimiseResumeSection(section, content);
        publishRecord(null, "resume_optimise", resumeId, section + ": " + content, result);
        return result;
    }

    private void publishRecord(Long userId, String scene, Long bizId,
                               String prompt, AiResult result) {
        try {
            AiChatRecord record = new AiChatRecord();
            record.setUserId(userId);
            record.setScene(scene);
            record.setBizId(bizId);
            record.setPrompt(truncate(prompt, 2000));
            record.setResponse(result.getContent());
            record.setModel(result.getModel());
            record.setPromptTokens(result.getPromptTokens());
            record.setCompletionTokens(result.getCompletionTokens());
            record.setTotalTokens(result.getTotalTokens());
            record.setElapsedMs(result.getElapsedMs());
            record.setStatus(1);
            eventPublisher.publishEvent(new AiChatRecordEvent(this, record));
        } catch (Exception e) {
            log.warn("Failed to publish AI chat record event", e);
        }
    }

    private String truncate(String s, int max) {
        if (s == null) return null;
        return s.length() > max ? s.substring(0, max) : s;
    }
}
