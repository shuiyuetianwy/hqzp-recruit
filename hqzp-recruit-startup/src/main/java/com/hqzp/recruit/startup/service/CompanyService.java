package com.hqzp.recruit.startup.service;

import com.hqzp.recruit.common.domain.entity.Company;
import com.hqzp.recruit.common.domain.query.PageQuery;
import com.hqzp.recruit.common.result.PageResult;

public interface CompanyService {

    PageResult<Company> page(PageQuery query, String keyword, String industry);

    Company getById(Long id);

    Long save(Company company);
}
