package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.common.annotation.Log;
import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.domain.dto.ResumeDTO;
import com.hqzp.recruit.common.domain.vo.ResumeVO;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.startup.service.ResumeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name =  "简历管理")
@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary ="获取我的简历")
    @RequireLogin(userTypes = {3})
    @GetMapping("/mine")
    public R<ResumeVO> mine() {
        return R.ok(resumeService.getMyResume());
    }

    @Operation(summary ="简历详情（HR查看）")
    @RequireLogin(userTypes = {1, 2})
    @GetMapping("/{id}")
    public R<ResumeVO> detail(@PathVariable Long id) {
        return R.ok(resumeService.getById(id));
    }

    @Operation(summary ="保存简历（新建或更新）")
    @Log(module = "简历", operation = "保存")
    @RequireLogin(userTypes = {3})
    @PostMapping
    public R<Long> save(@Validated @RequestBody ResumeDTO dto) {
        return R.ok(resumeService.saveResume(dto));
    }

    @Operation(summary ="删除简历")
    @Log(module = "简历", operation = "删除")
    @RequireLogin(userTypes = {3})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        resumeService.delete(id);
        return R.ok();
    }
}
