package com.hqzp.recruit.startup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqzp.recruit.common.domain.entity.Job;
import com.hqzp.recruit.common.domain.query.JobQuery;
import com.hqzp.recruit.common.domain.vo.JobVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JobMapper extends BaseMapper<Job> {

    /**
     * Paginated job search with company info joined.
     */
    IPage<JobVO> selectJobPage(Page<JobVO> page, @Param("q") JobQuery query);

    /**
     * Job detail with company info joined.
     */
    JobVO selectJobDetail(@Param("id") Long id);
}
