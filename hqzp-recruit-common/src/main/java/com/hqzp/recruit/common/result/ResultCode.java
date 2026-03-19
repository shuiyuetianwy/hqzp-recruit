package com.hqzp.recruit.common.result;

import lombok.Getter;

/**
 * Unified response codes.
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    CREATED(201, "创建成功"),
    NO_CONTENT(204, "删除成功"),

    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或Token已过期"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "数据已存在"),
    UNPROCESSABLE_ENTITY(422, "参数校验失败"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    // Business codes
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_DISABLED(1002, "用户已被禁用"),
    USER_PASSWORD_ERROR(1003, "用户名或密码错误"),
    USER_ALREADY_EXISTS(1004, "用户已存在"),

    JOB_NOT_FOUND(2001, "职位不存在"),
    JOB_CLOSED(2002, "职位已关闭"),

    RESUME_NOT_FOUND(3001, "简历不存在"),
    RESUME_ALREADY_SUBMITTED(3002, "已投递该职位"),

    FILE_UPLOAD_FAILED(4001, "文件上传失败"),
    FILE_NOT_FOUND(4002, "文件不存在"),
    FILE_TYPE_NOT_ALLOWED(4003, "文件类型不允许"),
    FILE_SIZE_EXCEEDED(4004, "文件大小超出限制"),

    AI_SERVICE_ERROR(5001, "AI服务异常"),
    AI_QUOTA_EXCEEDED(5002, "AI调用额度不足");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
