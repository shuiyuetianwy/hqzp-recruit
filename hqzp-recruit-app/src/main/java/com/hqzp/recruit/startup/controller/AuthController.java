package com.hqzp.recruit.startup.controller;

import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.domain.dto.LoginDTO;
import com.hqzp.recruit.common.domain.dto.RegisterDTO;
import com.hqzp.recruit.common.domain.vo.LoginVO;
import com.hqzp.recruit.common.result.R;
import com.hqzp.recruit.common.utils.SecurityUtils;
import com.hqzp.recruit.startup.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "认证")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R<LoginVO> login(@Validated @RequestBody LoginDTO dto) {
        return R.ok(authService.login(dto));
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return R.ok();
    }

    @ApiOperation("退出登录")
    @RequireLogin
    @PostMapping("/logout")
    public R<Void> logout() {
        authService.logout(SecurityUtils.getCurrentUserId());
        return R.ok();
    }
}
