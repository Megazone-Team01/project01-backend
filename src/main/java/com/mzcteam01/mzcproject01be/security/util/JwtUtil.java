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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    private final String key;

    public JwtUtil(@Value("${jwt.secret-key}") String key) {
        this.key = key;
    }

    public String generateToken(Map<String, Object> claims, int min) {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> validateToken(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
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
        } catch (JwtException e) {
            log.info("JWT error");
            throw new CustomJwtException(JwtErrorCode.JWT_ERROR);
        } catch (Exception e) {
            log.info("token error");
            throw new CustomJwtException(JwtErrorCode.GENERAL_ERROR);
        }
    }
}
