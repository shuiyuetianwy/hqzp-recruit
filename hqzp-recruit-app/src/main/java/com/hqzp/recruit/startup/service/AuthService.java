package com.hqzp.recruit.startup.service;

import com.hqzp.recruit.common.domain.dto.LoginDTO;
import com.hqzp.recruit.common.domain.dto.RegisterDTO;
import com.hqzp.recruit.common.domain.vo.LoginVO;

public interface AuthService {

    LoginVO login(LoginDTO dto);

    void register(RegisterDTO dto);

    void logout(Long userId);
}
