package com.hqzp.recruit.common.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResumeVO {

    private Long id;

    private Long userId;

    private String realName;

    private Integer gender;

    private Integer age;

    private String phone;

    private String email;

    private String city;

    private String avatar;

    private String currentTitle;

    private Integer experienceYears;

    private String education;

    private String summary;

    private Integer aiScore;

    private String aiAnalysis;

    private String attachmentKey;

    private String attachmentName;

    private String attachmentUrl;

    private Integer visibility;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<WorkExpVO> workExps;

    private List<EduExpVO> eduExps;

    @Data
    public static class WorkExpVO {
        private Long id;
        private String companyName;
        private String jobTitle;
        private String startDate;
        private String endDate;
        private Boolean isCurrent;
        private String description;
    }

    @Data
    public static class EduExpVO {
        private Long id;
        private String schoolName;
        private String major;
        private String degree;
        private String startDate;
        private String endDate;
        private String description;
    }
}
