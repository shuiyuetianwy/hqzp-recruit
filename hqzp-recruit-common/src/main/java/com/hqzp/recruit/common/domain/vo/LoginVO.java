package com.hqzp.recruit.common.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private Integer userType;
}
