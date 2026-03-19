package com.hqzp.recruit.startup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzp.recruit.common.domain.dto.ResumeDTO;
import com.hqzp.recruit.common.domain.entity.Resume;
import com.hqzp.recruit.common.domain.entity.ResumeEduExp;
import com.hqzp.recruit.common.domain.entity.ResumeWorkExp;
import com.hqzp.recruit.common.domain.vo.ResumeVO;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.common.utils.SecurityUtils;
import com.hqzp.recruit.startup.mapper.ResumeEduExpMapper;
import com.hqzp.recruit.startup.mapper.ResumeMapper;
import com.hqzp.recruit.startup.mapper.ResumeWorkExpMapper;
import com.hqzp.recruit.startup.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeMapper resumeMapper;
    private final ResumeWorkExpMapper workExpMapper;
    private final ResumeEduExpMapper eduExpMapper;

    @Override
    public ResumeVO getMyResume() {
        Long userId = SecurityUtils.getCurrentUserId();
        Resume resume = resumeMapper.selectOne(new LambdaQueryWrapper<Resume>()
                .eq(Resume::getUserId, userId)
                .eq(Resume::getDeleted, 0)
                .last("LIMIT 1"));
        if (resume == null) {
            return null;
        }
        return resumeMapper.selectResumeDetail(resume.getId());
    }

    @Override
    public ResumeVO getById(Long id) {
        ResumeVO vo = resumeMapper.selectResumeDetail(id);
        if (vo == null) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveResume(ResumeDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();

        Resume resume;
        boolean isNew = (dto.getId() == null);
        if (isNew) {
            resume = new Resume();
            resume.setUserId(userId);
        } else {
            resume = resumeMapper.selectById(dto.getId());
            if (resume == null || resume.getDeleted() == 1) {
                throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
            }
        }

        resume.setRealName(dto.getRealName());
        resume.setGender(dto.getGender());
        resume.setAge(dto.getAge());
        resume.setPhone(dto.getPhone());
        resume.setEmail(dto.getEmail());
        resume.setCity(dto.getCity());
        resume.setCurrentTitle(dto.getCurrentTitle());
        resume.setExperienceYears(dto.getExperienceYears());
        resume.setEducation(dto.getEducation());
        resume.setSummary(dto.getSummary());
        resume.setVisibility(dto.getVisibility() != null ? dto.getVisibility() : 1);

        if (isNew) {
            resumeMapper.insert(resume);
        } else {
            resumeMapper.updateById(resume);
            // Delete old sub-records before re-inserting
            workExpMapper.delete(new LambdaQueryWrapper<ResumeWorkExp>()
                    .eq(ResumeWorkExp::getResumeId, resume.getId()));
            eduExpMapper.delete(new LambdaQueryWrapper<ResumeEduExp>()
                    .eq(ResumeEduExp::getResumeId, resume.getId()));
        }

        // Insert work experiences
        if (!CollectionUtils.isEmpty(dto.getWorkExps())) {
            for (ResumeDTO.WorkExpDTO w : dto.getWorkExps()) {
                ResumeWorkExp exp = new ResumeWorkExp();
                exp.setResumeId(resume.getId());
                exp.setCompanyName(w.getCompanyName());
                exp.setJobTitle(w.getJobTitle());
                exp.setStartDate(parseDate(w.getStartDate()));
                exp.setEndDate(parseDate(w.getEndDate()));
                exp.setIsCurrent(Boolean.TRUE.equals(w.getIsCurrent()));
                exp.setDescription(w.getDescription());
                workExpMapper.insert(exp);
            }
        }

        // Insert education experiences
        if (!CollectionUtils.isEmpty(dto.getEduExps())) {
            for (ResumeDTO.EduExpDTO e : dto.getEduExps()) {
                ResumeEduExp exp = new ResumeEduExp();
                exp.setResumeId(resume.getId());
                exp.setSchoolName(e.getSchoolName());
                exp.setMajor(e.getMajor());
                exp.setDegree(e.getDegree());
                exp.setStartDate(parseDate(e.getStartDate()));
                exp.setEndDate(parseDate(e.getEndDate()));
                exp.setDescription(e.getDescription());
                eduExpMapper.insert(exp);
            }
        }

        return resume.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Resume resume = resumeMapper.selectById(id);
        if (resume == null || resume.getDeleted() == 1) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }
        resume.setDeleted(1);
        resumeMapper.updateById(resume);
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isEmpty()) return null;
        return LocalDate.parse(date);
    }
}
