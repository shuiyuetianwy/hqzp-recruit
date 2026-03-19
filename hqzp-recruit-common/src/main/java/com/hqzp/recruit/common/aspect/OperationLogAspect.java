package com.hqzp.recruit.common.aspect;

import com.hqzp.recruit.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Records operation logs for methods annotated with {@link Log}.
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Around("@annotation(com.hqzp.recruit.common.annotation.Log)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        String ip = getClientIp();
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();

        Object result = null;
        try {
            result = point.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("[OperationLog] module={} operation={} class={} method={} ip={} elapsed={}ms",
                    logAnnotation.module(), logAnnotation.operation(),
                    className, methodName, ip, elapsed);
            return result;
        } catch (Throwable e) {
            log.error("[OperationLog] module={} operation={} class={} method={} ip={} error={}",
                    logAnnotation.module(), logAnnotation.operation(),
                    className, methodName, ip, e.getMessage());
            throw e;
        }
    }

    private String getClientIp() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return "unknown";
            HttpServletRequest request = attrs.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        } catch (Exception e) {
            return "unknown";
        }
    }
}
