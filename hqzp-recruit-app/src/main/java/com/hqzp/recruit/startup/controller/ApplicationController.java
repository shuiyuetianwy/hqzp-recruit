package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.common.annotation.Log;
import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.domain.query.ApplicationQuery;
import com.hqzp.recruit.common.domain.vo.ApplicationVO;
import com.hqzp.recruit.common.result.PageResult;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.startup.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name =  "投递管理")
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary ="投递职位")
    @Log(module = "投递", operation = "投递")
    @RequireLogin(userTypes = {3})
    @PostMapping
    public R<Long> apply(@RequestBody ApplyRequest req) {
        return R.ok(applicationService.apply(req.getJobId(), req.getResumeId()));
    }

    @Operation(summary ="HR查看投递列表")
    @RequireLogin(userTypes = {1, 2})
    @GetMapping("/hr")
    public R<PageResult<ApplicationVO>> pageForHr(ApplicationQuery query) {
        return R.ok(applicationService.pageForHr(query));
    }

    @Operation(summary ="求职者查看投递记录")
    @RequireLogin(userTypes = {3})
    @GetMapping("/candidate")
    public R<PageResult<ApplicationVO>> pageForCandidate(ApplicationQuery query) {
        return R.ok(applicationService.pageForCandidate(query));
    }

    @Operation(summary ="更新投递状态（HR操作）")
    @Log(module = "投递", operation = "更新状态")
    @RequireLogin(userTypes = {1, 2})
    @PutMapping("/{id}/status")
    public R<Void> updateStatus(@PathVariable Long id,
                                @RequestBody StatusRequest req) {
        applicationService.updateStatus(id, req.getStatus(), req.getRemark());
        return R.ok();
    }

    @Operation(summary ="安排面试")
    @Log(module = "投递", operation = "安排面试")
    @RequireLogin(userTypes = {1, 2})
    @PutMapping("/{id}/interview")
    public R<Void> scheduleInterview(@PathVariable Long id,
                                     @RequestBody InterviewRequest req) {
        applicationService.scheduleInterview(id, req.getInterviewTime(), req.getLocation());
        return R.ok();
    }

    @Operation(summary ="放弃投递（求职者操作）")
    @Log(module = "投递", operation = "放弃")
    @RequireLogin(userTypes = {3})
    @PutMapping("/{id}/abandon")
    public R<Void> abandon(@PathVariable Long id) {
        applicationService.abandon(id);
        return R.ok();
    }

    @Data
    public static class ApplyRequest {
        private Long jobId;
        private Long resumeId;
    }

    @Data
    public static class StatusRequest {
        private Integer status;
        private String remark;
    }

    @Data
    public static class InterviewRequest {
        private String interviewTime;
        private String location;
    }
}
