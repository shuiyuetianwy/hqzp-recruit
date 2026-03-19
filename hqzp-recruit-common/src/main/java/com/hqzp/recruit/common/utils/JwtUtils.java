package com.hqzp.recruit.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT token generation and parsing utilities.
 */
@Slf4j
public final class JwtUtils {

    private JwtUtils() {}

    /**
     * Generate a signed JWT.
     *
     * @param claims  payload claims
     * @param secret  HMAC-SHA256 secret (min 32 chars)
     * @param ttlMs   token lifetime in milliseconds
     */
    public static String generate(Map<String, Object> claims, String secret, long ttlMs) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + ttlMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Parse and validate a JWT, returning its claims.
     *
     * @throws JwtException if the token is invalid or expired
     */
    public static Claims parse(String token, String secret) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Returns true if the token can be parsed without error.
     */
    public static boolean isValid(String token, String secret) {
        try {
            parse(token, secret);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }
}
