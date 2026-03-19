package com.hqzp.recruit.common.domain.query;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Base pagination parameters.
 */
@Data
public class PageQuery {

    @Min(value = 1, message = "页码最小为1")
    private long current = 1;

    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 100, message = "每页条数最大为100")
    private long size = 10;

    private String orderBy;

    /** asc / desc */
    private String orderDirection = "desc";
}
