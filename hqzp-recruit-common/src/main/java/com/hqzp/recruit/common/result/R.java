package com.hqzp.recruit.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Unified HTTP response wrapper.
 *
 * @param <T> payload type
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;
    private Long timestamp;

    private R() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> R<T> ok() {
        return result(ResultCode.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return result(ResultCode.SUCCESS, data);
    }

    public static <T> R<T> ok(String message, T data) {
        R<T> r = result(ResultCode.SUCCESS, data);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return result(resultCode, null);
    }

    public static <T> R<T> fail(ResultCode resultCode, String message) {
        R<T> r = result(resultCode, null);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> fail(int code, String message) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    private static <T> R<T> result(ResultCode resultCode, T data) {
        R<T> r = new R<>();
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        r.setData(data);
        return r;
    }

    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }
}
