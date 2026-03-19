package com.hqzp.recruit.common.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatusEnum {

    PENDING(0, "待查看"),
    VIEWED(1, "已查看"),
    INTERVIEW_INVITED(2, "邀请面试"),
    INTERVIEWING(3, "面试中"),
    OFFERED(4, "已录用"),
    REJECTED(5, "已拒绝"),
    ABANDONED(6, "已放弃");

    private final int code;
    private final String label;

    ApplicationStatusEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static String labelOf(int code) {
        for (ApplicationStatusEnum e : values()) {
            if (e.code == code) return e.label;
        }
        return "未知";
    }
}
