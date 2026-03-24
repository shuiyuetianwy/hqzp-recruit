package com.hqzp.recruit.startup.service;

import com.hqzp.recruit.common.domain.dto.ResumeDTO;
import com.hqzp.recruit.common.domain.vo.ResumeVO;

public interface ResumeService {

    ResumeVO getMyResume();

    ResumeVO getById(Long id);

    Long saveResume(ResumeDTO dto);

    void delete(Long id);
}
