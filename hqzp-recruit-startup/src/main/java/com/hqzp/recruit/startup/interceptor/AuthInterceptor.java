package com.hqzp.recruit.startup.interceptor;

import com.hqzp.recruit.common.annotation.RequireLogin;
import com.hqzp.recruit.common.constant.CommonConstants;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.ResultCode;
import com.hqzp.recruit.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Validates JWT tokens and enforces {@link RequireLogin} access control.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // Resolve @RequireLogin from method, then class
        RequireLogin requireLogin = method.getAnnotation(RequireLogin.class);
        if (requireLogin == null) {
            requireLogin = handlerMethod.getBeanType().getAnnotation(RequireLogin.class);
        }
        if (requireLogin == null) {
            return true;
        }

        // Extract token
        String header = request.getHeader(CommonConstants.AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(header) || !header.startsWith(CommonConstants.TOKEN_PREFIX)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        String token = header.substring(CommonConstants.TOKEN_PREFIX.length());

        // Validate and parse
        Claims claims;
        try {
            claims = JwtUtils.parse(token, jwtSecret);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        Long userId = claims.get("userId", Long.class);
        Integer userType = claims.get("userType", Integer.class);

        if (userId == null || userType == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        // Check user type restriction
        int[] allowedTypes = requireLogin.userTypes();
        if (allowedTypes.length > 0) {
            boolean allowed = Arrays.stream(allowedTypes).anyMatch(t -> t == userType);
            if (!allowed) {
                throw new BusinessException(ResultCode.FORBIDDEN);
            }
        }

        // Expose to downstream via request attributes
        request.setAttribute("userId", userId);
        request.setAttribute("userType", userType);
        return true;
    }
}
