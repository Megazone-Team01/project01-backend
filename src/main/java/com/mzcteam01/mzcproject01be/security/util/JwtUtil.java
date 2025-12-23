package com.mzcteam01.mzcproject01be.security.util;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import com.mzcteam01.mzcproject01be.common.exception.CustomJwtException;
import com.mzcteam01.mzcproject01be.common.exception.JwtErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    private static String key = "BdangfdFdjfb3246fscZsccxSfg356FSDHRedg";

    // generate JWT Token
    public static String generateToken(Map<String, Object> claims, int min) {

        SecretKey secretKey = null;

        try {
            secretKey = Keys.hmacShaKeyFor(JwtUtil.key.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // ZoneDateTime은 UTC 사용하는데 "Asia/Seoul"로 바뀜
        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();

    }

    // validate JWT token
    public static Map<String, Object> validateToken(String token) {

        Map<String, Object> claims = null;

        try {
            // 비밀키 : 토큰 위변조 확인
            SecretKey secretKey = Keys.hmacShaKeyFor(JwtUtil.key.getBytes("utf-8"));

            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (MalformedJwtException e) {
            log.info("MalformedJwtException");
            throw new CustomJwtException(JwtErrorCode.MALFORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("token expired");
            throw e;
        } catch (InvalidClaimException e) {
            log.info("token invalid");
            throw new CustomJwtException(JwtErrorCode.INVALID_CLAIM);
        } catch (io.jsonwebtoken.JwtException e) { // 서명 검증 실패 등
            log.info("token expired");
            throw new CustomJwtException(JwtErrorCode.JWT_ERROR);
        } catch (Exception e) {
            log.info("token error");
            throw new CustomJwtException(JwtErrorCode.GENERAL_ERROR);
        }

        return claims;
    }

}
