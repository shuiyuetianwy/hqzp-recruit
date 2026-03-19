package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.ai.dto.AiResult;
import com.hqzp.recruit.ai.service.AiRecruitService;
import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.domain.vo.ResumeVO;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.common.utils.SecurityUtils;
import com.hqzp.recruit.startup.service.ResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "AI功能")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiRecruitService aiRecruitService;
    private final ResumeService resumeService;

    @ApiOperation("AI分析简历")
    @RequireLogin
    @PostMapping("/resume/analyse/{resumeId}")
    public R<AiResult> analyseResume(@PathVariable Long resumeId) {
        ResumeVO resume = resumeService.getById(resumeId);
        String resumeText = buildResumeText(resume);
        return R.ok(aiRecruitService.analyseResume(resumeId, resumeText));
    }

    @ApiOperation("AI职位匹配评分")
    @RequireLogin(userTypes = {1, 2})
    @PostMapping("/match")
    public R<AiResult> matchJobResume(@RequestBody MatchRequest req) {
        return R.ok(aiRecruitService.matchJobResume(
                req.getApplicationId(), req.getJobDescription(), req.getResumeText()));
    }

    @ApiOperation("AI生成面试题")
    @RequireLogin(userTypes = {1, 2})
    @PostMapping("/interview-questions")
    public R<AiResult> generateInterviewQuestions(@RequestBody InterviewQuestionsRequest req) {
        int count = req.getCount() != null ? req.getCount() : 10;
        return R.ok(aiRecruitService.generateInterviewQuestions(
                req.getApplicationId(), req.getJobDescription(), req.getResumeText(), count));
    }

    @ApiOperation("AI职业发展建议")
    @RequireLogin(userTypes = {3})
    @PostMapping("/career-advice")
    public R<AiResult> careerAdvice(@RequestBody CareerAdviceRequest req) {
        Long userId = SecurityUtils.getCurrentUserId();
        ResumeVO resume = resumeService.getMyResume();
        String resumeText = resume != null ? buildResumeText(resume) : "";
        return R.ok(aiRecruitService.careerAdvice(userId, resumeText, req.getTargetJob()));
    }

    @ApiOperation("AI优化简历片段")
    @RequireLogin(userTypes = {3})
    @PostMapping("/resume/optimise")
    public R<AiResult> optimiseResumeSection(@RequestBody OptimiseRequest req) {
        ResumeVO resume = resumeService.getMyResume();
        Long resumeId = resume != null ? resume.getId() : null;
        return R.ok(aiRecruitService.optimiseResumeSection(resumeId, req.getSection(), req.getContent()));
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------

    private String buildResumeText(ResumeVO r) {
        StringBuilder sb = new StringBuilder();
        sb.append("姓名：").append(r.getRealName()).append("\n");
        if (r.getCurrentTitle() != null) sb.append("当前职位：").append(r.getCurrentTitle()).append("\n");
        if (r.getExperienceYears() != null) sb.append("工作年限：").append(r.getExperienceYears()).append("年\n");
        if (r.getEducation() != null) sb.append("最高学历：").append(r.getEducation()).append("\n");
        if (r.getCity() != null) sb.append("所在城市：").append(r.getCity()).append("\n");
        if (r.getSummary() != null) sb.append("自我介绍：").append(r.getSummary()).append("\n");

        if (r.getWorkExps() != null && !r.getWorkExps().isEmpty()) {
            sb.append("\n工作经历：\n");
            for (ResumeVO.WorkExpVO w : r.getWorkExps()) {
                sb.append("- ").append(w.getCompanyName()).append(" | ").append(w.getJobTitle())
                  .append(" (").append(w.getStartDate()).append(" ~ ")
                  .append(Boolean.TRUE.equals(w.getIsCurrent()) ? "至今" : w.getEndDate()).append(")\n");
                if (w.getDescription() != null) sb.append("  ").append(w.getDescription()).append("\n");
            }
        }

        if (r.getEduExps() != null && !r.getEduExps().isEmpty()) {
            sb.append("\n教育经历：\n");
            for (ResumeVO.EduExpVO e : r.getEduExps()) {
                sb.append("- ").append(e.getSchoolName()).append(" | ").append(e.getMajor())
                  .append(" | ").append(e.getDegree()).append("\n");
            }
        }
        return sb.toString();
    }

    @Data public static class MatchRequest {
        private Long applicationId;
        private String jobDescription;
        private String resumeText;
    }

    @Data public static class InterviewQuestionsRequest {
        private Long applicationId;
        private String jobDescription;
        private String resumeText;
        private Integer count;
    }

    @Data public static class CareerAdviceRequest {
        private String targetJob;
    }

    @Data public static class OptimiseRequest {
        private String section;
        private String content;
    }
}
