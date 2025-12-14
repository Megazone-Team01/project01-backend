package com.mzcteam01.mzcproject01be.security.filter;

import com.google.gson.Gson;
import com.mzcteam01.mzcproject01be.common.exception.CustomJwtException;
import com.mzcteam01.mzcproject01be.common.exception.JwtErrorCode;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRoleRepository userRoleRepository;

    // 필터 적용 여부
    // return 값에 따라 ture : 필터 미적용. false : 필터 적용.
    @Override
    protected  boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (method.equalsIgnoreCase("OPTIONS") || path.equals("/api/v1/user/sign") || path.equals("/api/v1/user/login") || path.startsWith("/api/v1/refresh")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");


        try {
            // 1. JWT 토큰 추출 : Bearer [JWT토큰문자열] 중 JWT 토큰 문자열만 추출하기 위함.
            String accessToken = authHeader.substring(7);

            // 2. 토큰 검증 후 클레임 파싱
            Map<String, Object> claims = JwtUtil.validateToken(accessToken);

            int id = (int) claims.get("id");
            String email = (String) claims.get("email");
            String name = (String) claims.get("name");
            String roleName = (String) claims.get("role");

            // 3. DB에서 Role 엔티티 조회
            UserRole role = userRoleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            // 4. AuthUser Dto에 정보 세팅
            AuthUser authUser = new AuthUser(id, email, name, role.getName());

            // 5. Spring Security 권한 리스트 생성
            List<GrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + roleName));

            // 6. Security 인증 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser, null, authorities);

            // 7. SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
            log.info("Authentication in context: {}", SecurityContextHolder.getContext().getAuthentication());
            log.info("Authentication 객체: {}", SecurityContextHolder.getContext().getAuthentication());
            log.info("Granted Authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());


        } catch (Exception e) {

            Throwable cause = e.getCause();

            if(cause instanceof AccessDeniedException) {
                throw new CustomJwtException(JwtErrorCode.ACCESS_DENIED);
            } else {
                Gson gson = new Gson();

                JwtErrorCode errorCode = JwtErrorCode.JWT_ERROR;

                String jsonStr = gson.toJson(Map.of("ERROR", errorCode.getMessage()));

                response.setContentType("application/json;charset=UTF-8");
                PrintWriter pw = response.getWriter();
                pw.println(jsonStr);
                pw.close();
            }
        }
    }

}
