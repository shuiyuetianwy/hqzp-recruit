package com.hqzp.recruit.startup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzp.recruit.common.constant.CacheConstants;
import com.hqzp.recruit.common.domain.dto.LoginDTO;
import com.hqzp.recruit.common.domain.dto.RegisterDTO;
import com.hqzp.recruit.common.domain.entity.SysUser;
import com.hqzp.recruit.common.domain.vo.LoginVO;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.common.utils.JwtUtils;
import com.hqzp.recruit.startup.mapper.SysUserMapper;
import com.hqzp.recruit.startup.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.ttl-ms}")
    private long jwtTtlMs;

    @Override
    public LoginVO login(LoginDTO dto) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, dto.getUsername())
                .eq(SysUser::getDeleted, 0));

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
        }
        if (user.getStatus() == 0) {
            throw new BusinessException(ResultCode.USER_DISABLED);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userType", user.getUserType());
        String token = JwtUtils.generate(claims, jwtSecret, jwtTtlMs);

        // Cache token for active-session tracking / forced logout
        redisTemplate.opsForValue().set(
                CacheConstants.TOKEN_PREFIX + user.getId(),
                token,
                CacheConstants.TOKEN_TTL,
                TimeUnit.SECONDS
        );

        return LoginVO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtTtlMs / 1000)
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .userType(user.getUserType())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO dto) {
        long count = userMapper.selectCount(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, dto.getUsername())
                .eq(SysUser::getDeleted, 0));
        if (count > 0) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
        }

        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setUserType(dto.getUserType());
        user.setStatus(1);
        userMapper.insert(user);
    }

    @Override
    public void logout(Long userId) {
        redisTemplate.delete(CacheConstants.TOKEN_PREFIX + userId);
    }
}
