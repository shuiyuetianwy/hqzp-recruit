package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.file.dto.UploadResult;
import com.hqzp.recruit.file.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @ApiOperation("上传头像")
    @RequireLogin
    @PostMapping("/avatar")
    public R<UploadResult> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return R.ok(fileStorageService.upload(file, "avatar", null));
    }

    @ApiOperation("上传简历附件（PDF/Word）")
    @RequireLogin(userTypes = {3})
    @PostMapping("/resume")
    public R<UploadResult> uploadResume(@RequestParam("file") MultipartFile file,
                                        @RequestParam(required = false) Long resumeId) {
        return R.ok(fileStorageService.upload(file, "resume", resumeId));
    }

    @ApiOperation("上传公司Logo")
    @RequireLogin(userTypes = {1, 2})
    @PostMapping("/company-logo")
    public R<UploadResult> uploadCompanyLogo(@RequestParam("file") MultipartFile file,
                                             @RequestParam(required = false) Long companyId) {
        return R.ok(fileStorageService.upload(file, "company_logo", companyId));
    }

    @ApiOperation("通用文件上传")
    @RequireLogin
    @PostMapping("/upload")
    public R<UploadResult> upload(@RequestParam("file") MultipartFile file,
                                  @RequestParam(defaultValue = "other") String bizType,
                                  @RequestParam(required = false) Long bizId) {
        return R.ok(fileStorageService.upload(file, bizType, bizId));
    }

    @ApiOperation("获取文件预签名URL")
    @RequireLogin
    @GetMapping("/presigned-url")
    public R<String> presignedUrl(@RequestParam String fileKey) {
        return R.ok(fileStorageService.generatePresignedUrl(fileKey));
    }

    @ApiOperation("删除文件")
    @RequireLogin(userTypes = {1})
    @DeleteMapping
    public R<Void> delete(@RequestParam String fileKey) {
        fileStorageService.delete(fileKey);
        return R.ok();
    }
}
