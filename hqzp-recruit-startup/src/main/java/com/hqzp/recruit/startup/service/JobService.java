package com.hqzp.recruit.startup.service;

import com.hqzp.recruit.common.domain.dto.JobDTO;
import com.hqzp.recruit.common.domain.query.JobQuery;
import com.hqzp.recruit.common.domain.vo.JobVO;
import com.hqzp.recruit.common.result.PageResult;

public interface JobService {

    PageResult<JobVO> page(JobQuery query);

    JobVO getById(Long id);

    Long create(JobDTO dto);

    void update(JobDTO dto);

    void publish(Long id);

    void close(Long id);

    void delete(Long id);
}
