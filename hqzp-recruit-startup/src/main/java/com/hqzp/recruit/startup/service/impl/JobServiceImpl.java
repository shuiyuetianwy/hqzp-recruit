package com.hqzp.recruit.startup.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqzp.recruit.common.domain.dto.JobDTO;
import com.hqzp.recruit.common.domain.entity.Job;
import com.hqzp.recruit.common.domain.query.JobQuery;
import com.hqzp.recruit.common.domain.vo.JobVO;
import com.hqzp.recruit.common.enums.JobStatusEnum;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.PageResult;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.common.utils.SecurityUtils;
import com.hqzp.recruit.startup.mapper.JobMapper;
import com.hqzp.recruit.startup.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;

    @Override
    public PageResult<JobVO> page(JobQuery query) {
        Page<JobVO> p = new Page<>(query.getCurrent(), query.getSize());
        jobMapper.selectJobPage(p, query);
        return PageResult.of(p.getTotal(), p.getRecords(), p.getCurrent(), p.getSize());
    }

    @Override
    public JobVO getById(Long id) {
        JobVO vo = jobMapper.selectJobDetail(id);
        if (vo == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }
        // Increment view count asynchronously (best-effort)
        Job update = new Job();
        update.setId(id);
        update.setViewCount(vo.getViewCount() == null ? 1 : vo.getViewCount() + 1);
        jobMapper.updateById(update);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(JobDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        Job job = new Job();
        copyDtoToEntity(dto, job);
        job.setPublisherId(userId);
        job.setStatus(JobStatusEnum.DRAFT.getCode());
        job.setViewCount(0);
        job.setDeliveryCount(0);
        jobMapper.insert(job);
        return job.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JobDTO dto) {
        Job existing = jobMapper.selectById(dto.getId());
        if (existing == null || existing.getDeleted() == 1) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }
        copyDtoToEntity(dto, existing);
        jobMapper.updateById(existing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long id) {
        Job job = requireJob(id);
        job.setStatus(JobStatusEnum.PUBLISHED.getCode());
        jobMapper.updateById(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void close(Long id) {
        Job job = requireJob(id);
        job.setStatus(JobStatusEnum.CLOSED.getCode());
        jobMapper.updateById(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Job job = requireJob(id);
        job.setDeleted(1);
        jobMapper.updateById(job);
    }

    private Job requireJob(Long id) {
        Job job = jobMapper.selectById(id);
        if (job == null || job.getDeleted() == 1) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }
        return job;
    }

    private void copyDtoToEntity(JobDTO dto, Job job) {
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setRequirement(dto.getRequirement());
        job.setCity(dto.getCity());
        job.setAddress(dto.getAddress());
        job.setSalaryMin(dto.getSalaryMin());
        job.setSalaryMax(dto.getSalaryMax());
        job.setExperience(dto.getExperience());
        job.setEducation(dto.getEducation());
        job.setCategory(dto.getCategory());
        job.setHeadcount(dto.getHeadcount());
        job.setJobType(dto.getJobType());
    }
}
