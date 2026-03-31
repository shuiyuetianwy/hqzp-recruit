package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.domain.entity.Company;
import com.hqzp.recruit.common.domain.query.PageQuery;
import com.hqzp.recruit.common.result.PageResult;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.startup.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name =  "企业管理")
@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary ="企业列表（分页）")
    @GetMapping
    public R<PageResult<Company>> page(PageQuery query,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String industry) {
        return R.ok(companyService.page(query, keyword, industry));
    }

    @Operation(summary ="企业详情")
    @GetMapping("/{id}")
    public R<Company> detail(@PathVariable Long id) {
        return R.ok(companyService.getById(id));
    }

    @Operation(summary ="创建/更新企业信息")
    @RequireLogin(userTypes = {1, 2})
    @PostMapping
    public R<Long> save(@RequestBody Company company) {
        return R.ok(companyService.save(company));
    }
}
