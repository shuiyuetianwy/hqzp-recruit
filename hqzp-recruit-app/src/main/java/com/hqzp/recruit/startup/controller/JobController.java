package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.common.annotation.Log;
import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.domain.dto.JobDTO;
import com.hqzp.recruit.common.domain.query.JobQuery;
import com.hqzp.recruit.common.domain.vo.JobVO;
import com.hqzp.recruit.common.result.PageResult;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.startup.service.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name =  "职位管理")
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @Operation(summary ="职位列表（分页）")
    @GetMapping
    public R<PageResult<JobVO>> page(JobQuery query) {
        return R.ok(jobService.page(query));
    }

    @Operation(summary ="职位详情")
    @GetMapping("/{id}")
    public R<JobVO> detail(@PathVariable Long id) {
        return R.ok(jobService.getById(id));
    }

    @Operation(summary ="发布职位")
    @Log(module = "职位", operation = "创建")
    @RequireLogin(userTypes = {2})
    @PostMapping
    public R<Long> create(@Validated @RequestBody JobDTO dto) {
        return R.ok(jobService.create(dto));
    }

    @Operation(summary ="编辑职位")
    @Log(module = "职位", operation = "编辑")
    @RequireLogin(userTypes = {2})
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody JobDTO dto) {
        dto.setId(id);
        jobService.update(dto);
        return R.ok();
    }

    @Operation(summary ="上架职位")
    @Log(module = "职位", operation = "上架")
    @RequireLogin(userTypes = {2})
    @PutMapping("/{id}/publish")
    public R<Void> publish(@PathVariable Long id) {
        jobService.publish(id);
        return R.ok();
    }

    @Operation(summary ="关闭职位")
    @Log(module = "职位", operation = "关闭")
    @RequireLogin(userTypes = {2})
    @PutMapping("/{id}/close")
    public R<Void> close(@PathVariable Long id) {
        jobService.close(id);
        return R.ok();
    }

    @Operation(summary ="删除职位")
    @Log(module = "职位", operation = "删除")
    @RequireLogin(userTypes = {1, 2})
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        jobService.delete(id);
        return R.ok();
    }
}
