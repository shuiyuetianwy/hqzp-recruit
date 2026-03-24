package com.hqzp.recruit.startup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqzp.recruit.common.domain.entity.Company;
import com.hqzp.recruit.common.domain.query.PageQuery;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.PageResult;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.startup.mapper.CompanyMapper;
import com.hqzp.recruit.startup.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;

    @Override
    public PageResult<Company> page(PageQuery query, String keyword, String industry) {
        Page<Company> p = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<Company>()
                .eq(Company::getDeleted, 0);
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Company::getName, keyword);
        }
        if (StringUtils.hasText(industry)) {
            wrapper.eq(Company::getIndustry, industry);
        }
        wrapper.orderByDesc(Company::getCreateTime);
        companyMapper.selectPage(p, wrapper);
        return PageResult.of(p.getTotal(), p.getRecords(), p.getCurrent(), p.getSize());
    }

    @Override
    public Company getById(Long id) {
        Company company = companyMapper.selectById(id);
        if (company == null || company.getDeleted() == 1) {
            throw new BusinessException(ResultCode.NOT_FOUND, "企业不存在");
        }
        return company;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(Company company) {
        if (company.getId() == null) {
            company.setVerifyStatus(0);
            companyMapper.insert(company);
        } else {
            companyMapper.updateById(company);
        }
        return company.getId();
    }
}
