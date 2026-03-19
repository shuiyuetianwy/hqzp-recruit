package com.hqzp.recruit.common.domain.query;

import lombok.Data;

@Data
public class ApplicationQuery extends PageQuery {

    private Long jobId;

    private Long candidateId;

    private Long companyId;

    private Integer status;

    private String keyword;
}
