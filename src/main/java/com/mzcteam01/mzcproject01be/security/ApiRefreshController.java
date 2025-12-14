package com.mzcteam01.mzcproject01be.security;

import com.mzcteam01.mzcproject01be.common.exception.JwtErrorCode;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ApiRefreshController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/refresh")
    public Map<String, Object> refresh(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("refreshToken") String refreshToken) {

        if (refreshToken == null || authHeader == null || authHeader.length() < 7) {
            JwtErrorCode errorCode = JwtErrorCode.MISSING_TOKEN;
            return Map.of("ERROR", errorCode.getMessage());
        }

        String accessToken = authHeader.substring(7);

        // Access Token이 만료되지 않은 경우 그대로 반환
        if (!isExpired(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // DB에서 Refresh Token 확인
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException(JwtErrorCode.INVALID_AUTH.getMessage()));

        // Refresh Token 만료 확인
        if (user.getRefreshTokenExpireAt().isBefore(LocalDateTime.now())) {
            return Map.of("ERROR", JwtErrorCode.INVALID_AUTH.getMessage());
        }

        // Access Token이 만료된 경우 Refresh Token 검증
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);
        log.info("claims: {}", claims);

        // 새로운 Access Token 생성 (10분)
        String newAccessToken = jwtUtil.generateToken(claims, 10);

        // Refresh Token 만료 임박 시 갱신 (180일)
        String newRefreshToken = isExpiringSoon((Integer) claims.get("exp"))
                ? jwtUtil.generateToken(claims, 180 * 24 * 60) // 180일
                : refreshToken;

        if (!newRefreshToken.equals(user.getRefreshToken())) {
            user.updateRefreshToken(newRefreshToken, LocalDateTime.now().plusDays(180));
            userRepository.save(user);
        }

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        );
    }

    /** Access Token 만료 여부 확인 */
    private boolean isExpired(String token) {
        try {
            jwtUtil.validateToken(token);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /** Refresh Token 만료 임박 여부 확인 (초 단위) */
    private boolean isExpiringSoon(Integer exp) {
        Date expDate = new Date(exp * 1000L);
        long gapMinutes = (expDate.getTime() - System.currentTimeMillis()) / (1000 * 60);
        return gapMinutes < 60; // 60분 미만이면 갱신
    }
}
