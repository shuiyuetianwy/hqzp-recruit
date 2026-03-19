package com.hqzp.recruit.common.constant;

/**
 * Shared application-wide constants.
 */
public final class CommonConstants {

    private CommonConstants() {}

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String USER_ID_HEADER = "X-User-Id";

    public static final String USER_TYPE_HEADER = "X-User-Type";

    public static final int STATUS_NORMAL = 1;

    public static final int STATUS_DISABLED = 0;

    public static final int DELETED_YES = 1;

    public static final int DELETED_NO = 0;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
