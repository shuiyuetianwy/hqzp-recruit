package com.hqzp.recruit.common.annotation;

import java.lang.annotation.*;

/**
 * Marks a controller method or class as requiring an authenticated user.
 * Enforced by {@code AuthInterceptor} in the startup module.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {

    /**
     * Allowed user types. Empty means any authenticated user.
     * Values correspond to {@code UserTypeEnum} codes: 1=admin, 2=hr, 3=candidate.
     */
    int[] userTypes() default {};
}
