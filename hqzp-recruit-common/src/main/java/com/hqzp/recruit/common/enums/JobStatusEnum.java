package com.hqzp.recruit.common.enums;

import lombok.Getter;

@Getter
public enum JobStatusEnum {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布"),
    CLOSED(2, "已关闭");

    private final int code;
    private final String label;

    JobStatusEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }
}
