package com.hqzp.recruit.startup.service;

import com.hqzp.recruit.common.domain.query.ApplicationQuery;
import com.hqzp.recruit.common.domain.vo.ApplicationVO;
import com.hqzp.recruit.common.result.PageResult;

public interface ApplicationService {

    /** Candidate submits an application. */
    Long apply(Long jobId, Long resumeId);

    /** HR pages through applications for their company's jobs. */
    PageResult<ApplicationVO> pageForHr(ApplicationQuery query);

    /** Candidate pages through their own applications. */
    PageResult<ApplicationVO> pageForCandidate(ApplicationQuery query);

    /** HR updates application status (view / invite / reject / offer). */
    void updateStatus(Long id, Integer status, String remark);

    /** HR schedules an interview. */
    void scheduleInterview(Long id, String interviewTime, String location);

    /** Candidate withdraws an application. */
    void abandon(Long id);
}
