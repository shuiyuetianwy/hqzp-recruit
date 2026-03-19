package com.hqzp.recruit.ai.service;

import com.hqzp.recruit.ai.dto.AiResult;

/**
 * High-level AI operations specific to the recruitment domain.
 * Implementations call {@link AiService} and persist results via callbacks.
 */
public interface AiRecruitService {

    /**
     * Analyse a resume and return the structured JSON result.
     *
     * @param resumeId  ID of the resume to analyse
     * @param resumeText  plain-text representation of the resume
     */
    AiResult analyseResume(Long resumeId, String resumeText);

    /**
     * Score the match between a job and a resume.
     *
     * @param applicationId  ID of the job application
     * @param jobDescription  job description text
     * @param resumeText  resume text
     */
    AiResult matchJobResume(Long applicationId, String jobDescription, String resumeText);

    /**
     * Generate interview questions for an application.
     *
     * @param applicationId  ID of the job application
     * @param jobDescription  job description text
     * @param resumeText  resume text
     * @param count  number of questions to generate
     */
    AiResult generateInterviewQuestions(Long applicationId, String jobDescription,
                                        String resumeText, int count);

    /**
     * Provide career development advice for a candidate.
     *
     * @param userId  candidate user ID
     * @param resumeText  resume text
     * @param targetJob  target job title
     */
    AiResult careerAdvice(Long userId, String resumeText, String targetJob);

    /**
     * Optimise a specific section of a resume.
     *
     * @param resumeId  resume ID
     * @param section  section name (e.g. 自我介绍, 工作经历)
     * @param content  current section content
     */
    AiResult optimiseResumeSection(Long resumeId, String section, String content);
}
