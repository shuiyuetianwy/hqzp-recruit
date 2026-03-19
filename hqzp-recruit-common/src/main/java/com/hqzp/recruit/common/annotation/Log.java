package com.hqzp.recruit.common.annotation;

import java.lang.annotation.*;

/**
 * Marks a controller method for operation log recording.
 * Processed by {@code OperationLogAspect}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** Business module name. */
    String module() default "";

    /** Operation description. */
    String operation() default "";
}
