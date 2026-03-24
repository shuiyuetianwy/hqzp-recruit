package com.hqzp.recruit.startup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqzp.recruit.common.domain.entity.Resume;
import com.hqzp.recruit.common.domain.vo.ResumeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ResumeMapper extends BaseMapper<Resume> {

    /**
     * Resume detail with work/edu experience joined.
     */
    ResumeVO selectResumeDetail(@Param("id") Long id);
}
