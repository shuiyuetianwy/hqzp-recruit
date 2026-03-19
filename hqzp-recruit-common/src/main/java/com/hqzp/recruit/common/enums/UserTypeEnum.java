package com.hqzp.recruit.common.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    ADMIN(1, "管理员"),
    HR(2, "HR"),
    CANDIDATE(3, "求职者");

    private final int code;
    private final String label;

    UserTypeEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static UserTypeEnum of(int code) {
        for (UserTypeEnum e : values()) {
            if (e.code == code) return e;
        }
        throw new IllegalArgumentException("Unknown user type: " + code);
    }
}
