package com.hqzp.recruit.startup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqzp.recruit.common.domain.entity.Job;
import com.hqzp.recruit.common.domain.entity.JobApplication;
import com.hqzp.recruit.common.domain.query.ApplicationQuery;
import com.hqzp.recruit.common.domain.vo.ApplicationVO;
import com.hqzp.recruit.common.enums.ApplicationStatusEnum;
import com.hqzp.recruit.common.enums.JobStatusEnum;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.PageResult;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.common.utils.SecurityUtils;
import com.hqzp.recruit.startup.mapper.JobApplicationMapper;
import com.hqzp.recruit.startup.mapper.JobMapper;
import com.hqzp.recruit.startup.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationMapper applicationMapper;
    private final JobMapper jobMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long apply(Long jobId, Long resumeId) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Job job = jobMapper.selectById(jobId);
        if (job == null || job.getDeleted() == 1) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }
        if (job.getStatus() != JobStatusEnum.PUBLISHED.getCode()) {
            throw new BusinessException(ResultCode.JOB_CLOSED);
        }

        // Prevent duplicate application
        long count = applicationMapper.selectCount(new LambdaQueryWrapper<JobApplication>()
                .eq(JobApplication::getJobId, jobId)
                .eq(JobApplication::getCandidateId, candidateId)
                .eq(JobApplication::getDeleted, 0));
        if (count > 0) {
            throw new BusinessException(ResultCode.RESUME_ALREADY_SUBMITTED);
        }

        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setCandidateId(candidateId);
        application.setResumeId(resumeId);
        application.setCompanyId(job.getCompanyId());
        application.setStatus(ApplicationStatusEnum.PENDING.getCode());
        applicationMapper.insert(application);

        // Increment delivery count
        Job update = new Job();
        update.setId(jobId);
        update.setDeliveryCount(job.getDeliveryCount() == null ? 1 : job.getDeliveryCount() + 1);
        jobMapper.updateById(update);

        return application.getId();
    }

    @Override
    public PageResult<ApplicationVO> pageForHr(ApplicationQuery query) {
        Page<ApplicationVO> p = new Page<>(query.getCurrent(), query.getSize());
        applicationMapper.selectApplicationPage(p, query);
        return PageResult.of(p.getTotal(), p.getRecords(), p.getCurrent(), p.getSize());
    }

    @Override
    public PageResult<ApplicationVO> pageForCandidate(ApplicationQuery query) {
        query.setCandidateId(SecurityUtils.getCurrentUserId());
        Page<ApplicationVO> p = new Page<>(query.getCurrent(), query.getSize());
        applicationMapper.selectApplicationPage(p, query);
        return PageResult.of(p.getTotal(), p.getRecords(), p.getCurrent(), p.getSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status, String remark) {
        JobApplication app = requireApplication(id);
        app.setStatus(status);
        if (remark != null) app.setHrRemark(remark);
        if (status == ApplicationStatusEnum.VIEWED.getCode() && app.getViewTime() == null) {
            app.setViewTime(LocalDateTime.now());
        }
        applicationMapper.updateById(app);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void scheduleInterview(Long id, String interviewTime, String location) {
        JobApplication app = requireApplication(id);
        app.setStatus(ApplicationStatusEnum.INTERVIEW_INVITED.getCode());
        app.setInterviewTime(LocalDateTime.parse(interviewTime));
        app.setInterviewLocation(location);
        applicationMapper.updateById(app);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void abandon(Long id) {
        JobApplication app = requireApplication(id);
        Long candidateId = SecurityUtils.getCurrentUserId();
        if (!app.getCandidateId().equals(candidateId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        app.setStatus(ApplicationStatusEnum.ABANDONED.getCode());
        applicationMapper.updateById(app);
    }

    private JobApplication requireApplication(Long id) {
        JobApplication app = applicationMapper.selectById(id);
        if (app == null || app.getDeleted() == 1) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return app;
    }
}
