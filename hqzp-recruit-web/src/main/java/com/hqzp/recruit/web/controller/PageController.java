package com.hqzp.recruit.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Serves Thymeleaf page templates. All data is loaded client-side via Vue + REST API.
 */
@Controller
public class PageController {

    // -------------------------------------------------------
    // Public pages
    // -------------------------------------------------------

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("pageTitle", "首页 - HQZP招聘");
        return "index";
    }

    @GetMapping("/jobs")
    public String jobList(Model model) {
        model.addAttribute("pageTitle", "职位列表 - HQZP招聘");
        return "job/list";
    }

    @GetMapping("/jobs/{id}")
    public String jobDetail(@PathVariable Long id, Model model) {
        model.addAttribute("jobId", id);
        model.addAttribute("pageTitle", "职位详情 - HQZP招聘");
        return "job/detail";
    }

    @GetMapping("/companies")
    public String companyList(Model model) {
        model.addAttribute("pageTitle", "企业列表 - HQZP招聘");
        return "company/list";
    }

    @GetMapping("/companies/{id}")
    public String companyDetail(@PathVariable Long id, Model model) {
        model.addAttribute("companyId", id);
        model.addAttribute("pageTitle", "企业详情 - HQZP招聘");
        return "company/detail";
    }

    // -------------------------------------------------------
    // Auth pages
    // -------------------------------------------------------

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(@RequestParam(defaultValue = "3") Integer type, Model model) {
        model.addAttribute("userType", type);
        return "auth/register";
    }

    // -------------------------------------------------------
    // Candidate pages
    // -------------------------------------------------------

    @GetMapping("/candidate/dashboard")
    public String candidateDashboard(Model model) {
        model.addAttribute("pageTitle", "求职者中心");
        return "candidate/dashboard";
    }

    @GetMapping("/candidate/resume")
    public String candidateResume(Model model) {
        model.addAttribute("pageTitle", "我的简历");
        return "candidate/resume";
    }

    @GetMapping("/candidate/applications")
    public String candidateApplications(Model model) {
        model.addAttribute("pageTitle", "投递记录");
        return "candidate/applications";
    }

    @GetMapping("/candidate/ai-assistant")
    public String candidateAiAssistant(Model model) {
        model.addAttribute("pageTitle", "AI职业助手");
        return "ai/assistant";
    }

    // -------------------------------------------------------
    // HR pages
    // -------------------------------------------------------

    @GetMapping("/hr/dashboard")
    public String hrDashboard(Model model) {
        model.addAttribute("pageTitle", "HR工作台");
        return "hr/dashboard";
    }

    @GetMapping("/hr/jobs")
    public String hrJobs(Model model) {
        model.addAttribute("pageTitle", "职位管理");
        return "hr/jobs";
    }

    @GetMapping("/hr/jobs/create")
    public String hrJobCreate(Model model) {
        model.addAttribute("pageTitle", "发布职位");
        return "hr/job-form";
    }

    @GetMapping("/hr/jobs/{id}/edit")
    public String hrJobEdit(@PathVariable Long id, Model model) {
        model.addAttribute("jobId", id);
        model.addAttribute("pageTitle", "编辑职位");
        return "hr/job-form";
    }

    @GetMapping("/hr/applications")
    public String hrApplications(Model model) {
        model.addAttribute("pageTitle", "投递管理");
        return "hr/applications";
    }

    @GetMapping("/hr/applications/{id}")
    public String hrApplicationDetail(@PathVariable Long id, Model model) {
        model.addAttribute("applicationId", id);
        model.addAttribute("pageTitle", "投递详情");
        return "hr/application-detail";
    }

    @GetMapping("/hr/company")
    public String hrCompany(Model model) {
        model.addAttribute("pageTitle", "公司信息");
        return "hr/company";
    }
}
