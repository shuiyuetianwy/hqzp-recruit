package com.hqzp.recruit.common.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ResumeDTO {

    private Long id;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    private Integer gender;

    private Integer age;

    private String phone;

    private String email;

    private String city;

    private String currentTitle;

    private Integer experienceYears;

    private String education;

    private String summary;

    private Integer visibility;

    private List<WorkExpDTO> workExps;

    private List<EduExpDTO> eduExps;

    @Data
    public static class WorkExpDTO {
        private Long id;
        private String companyName;
        private String jobTitle;
        private String startDate;
        private String endDate;
        private Boolean isCurrent;
        private String description;
    }

    @Data
    public static class EduExpDTO {
        private Long id;
        private String schoolName;
        private String major;
        private String degree;
        private String startDate;
        private String endDate;
        private String description;
    }
}
