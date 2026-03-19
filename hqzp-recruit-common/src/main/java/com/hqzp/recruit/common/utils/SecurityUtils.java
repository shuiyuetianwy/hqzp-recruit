package com.hqzp.recruit.common.utils;

import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.ResultCode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Retrieves the authenticated user context from the current request.
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    private static final String ATTR_USER_ID = "userId";
    private static final String ATTR_USER_TYPE = "userType";

    public static Long getCurrentUserId() {
        HttpServletRequest request = getRequest();
        Object userId = request.getAttribute(ATTR_USER_ID);
        if (userId == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        return Long.valueOf(userId.toString());
    }

    public static Integer getCurrentUserType() {
        HttpServletRequest request = getRequest();
        Object userType = request.getAttribute(ATTR_USER_TYPE);
        if (userType == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        return Integer.valueOf(userType.toString());
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        return attrs.getRequest();
    }
}
