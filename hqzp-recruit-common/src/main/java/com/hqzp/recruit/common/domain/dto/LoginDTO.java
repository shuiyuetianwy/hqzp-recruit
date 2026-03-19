package com.hqzp.recruit.common.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    /** Login type: 1=password, 2=sms_code. */
    private Integer loginType = 1;
}
