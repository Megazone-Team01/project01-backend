package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class DebugController {

    private final JwtUtil jwtUtil;

    // 보호된 엔드포인트
    @GetMapping("/check-role")
    public String checkRole(Authentication authentication) {
        if (authentication == null) {
            return "인증 정보 없음";
        }

        // Authentication 정보 전체 로그
        System.out.println("Authentication 객체: " + authentication);

        // 권한(Authority) 출력
        authentication.getAuthorities().forEach(auth ->
                System.out.println("권한 확인: " + auth.getAuthority())
        );

        // Principal(User 정보) 출력
        System.out.println("Principal: " + authentication.getPrincipal());

        return "권한 확인 완료";
    }

    @GetMapping("/some-protected-endpoint")
    public String testEndpoint(Authentication authentication) {
        log.info("SecurityContext Authentication: {}", authentication);
        return "OK";
    }

    // JWT payload 확인용 엔드포인트
    @GetMapping("/jwt-payload")
    public Map<String, Object> jwtPayload(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Map.of("ERROR", "Authorization header missing or invalid");
        }

        String token = authHeader.substring(7); // "Bearer " 제거
        try {
            // JwtUtil.validateToken을 통해 claims 확인
            Map<String, Object> claims = jwtUtil.validateToken(token);
            log.info("JWT payload: {}", claims);
            return claims;
        } catch (Exception e) {
            log.error("JWT 검증 실패", e);
            return Map.of("ERROR", e.getMessage());
        }
    }
}
