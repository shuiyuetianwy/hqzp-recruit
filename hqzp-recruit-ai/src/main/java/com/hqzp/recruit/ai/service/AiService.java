package com.hqzp.recruit.ai.service;

import com.hqzp.recruit.ai.dto.AiResult;
import com.hqzp.recruit.ai.dto.ChatMessage;

import java.util.List;

public interface AiService {

    /**
     * Send a list of messages and return the model's reply.
     */
    AiResult chat(List<ChatMessage> messages);

    /**
     * Analyse a resume text and return a structured JSON assessment.
     * Returns JSON with fields: score(0-100), strengths[], weaknesses[], suggestions[].
     */
    AiResult analyseResume(String resumeText);

    /**
     * Score how well a resume matches a job description.
     * Returns JSON with fields: score(0-100), matchPoints[], gapPoints[], recommendation.
     */
    AiResult matchJobResume(String jobDescription, String resumeText);

    /**
     * Generate interview questions tailored to a job description and resume.
     * Returns JSON with fields: questions[{question, type, difficulty}].
     */
    AiResult generateInterviewQuestions(String jobDescription, String resumeText, int count);

    /**
     * Provide career advice based on a candidate's resume and target job.
     */
    AiResult careerAdvice(String resumeText, String targetJob);

    /**
     * Optimise a resume section and return improved text.
     */
    AiResult optimiseResumeSection(String section, String content);
}
