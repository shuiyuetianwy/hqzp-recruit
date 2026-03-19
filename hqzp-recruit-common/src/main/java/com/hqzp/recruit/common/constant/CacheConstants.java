package com.hqzp.recruit.common.constant;

/**
 * Redis cache key prefixes and TTL constants.
 */
public final class CacheConstants {

    private CacheConstants() {}

    public static final String TOKEN_PREFIX = "recruit:token:";

    public static final String SMS_CODE_PREFIX = "recruit:sms:";

    public static final String JOB_DETAIL_PREFIX = "recruit:job:detail:";

    public static final String COMPANY_DETAIL_PREFIX = "recruit:company:detail:";

    public static final String USER_INFO_PREFIX = "recruit:user:info:";

    /** Token TTL in seconds (7 days). */
    public static final long TOKEN_TTL = 7 * 24 * 3600L;

    /** SMS code TTL in seconds (5 minutes). */
    public static final long SMS_CODE_TTL = 5 * 60L;

    /** Job detail cache TTL in seconds (10 minutes). */
    public static final long JOB_DETAIL_TTL = 10 * 60L;
}
