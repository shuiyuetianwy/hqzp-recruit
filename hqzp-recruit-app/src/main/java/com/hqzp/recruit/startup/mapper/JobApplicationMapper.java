package com.hqzp.recruit.startup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqzp.recruit.common.domain.entity.JobApplication;
import com.hqzp.recruit.common.domain.query.ApplicationQuery;
import com.hqzp.recruit.common.domain.vo.ApplicationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JobApplicationMapper extends BaseMapper<JobApplication> {

    IPage<ApplicationVO> selectApplicationPage(Page<ApplicationVO> page,
                                               @Param("q") ApplicationQuery query);
}
