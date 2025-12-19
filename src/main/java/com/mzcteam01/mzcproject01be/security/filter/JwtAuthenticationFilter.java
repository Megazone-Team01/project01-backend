package com.mzcteam01.mzcproject01be.security.filter;

import com.mzcteam01.mzcproject01be.common.exception.CustomJwtException;
import com.mzcteam01.mzcproject01be.common.exception.JwtErrorCode;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;


    // 필터 적용 여부
    // return 값에 따라 ture : 필터 미적용. false : 필터 적용.
    @Override
    protected  boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (method.equalsIgnoreCase("OPTIONS") || path.equals("/api/v1/user/signup") || path.equals("/api/v1/user/login") || path.startsWith("/api/v1/refresh")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        try{
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // 토큰이 없거나 잘못된 형식이면 필터 통과시키거나 401 리턴, 지금은 다음단계로
                filterChain.doFilter(request, response);
                return;
            }
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
//            log.info("Authentication in context: {}", SecurityContextHolder.getContext().getAuthentication());
//            log.info("Authentication 객체: {}", SecurityContextHolder.getContext().getAuthentication());
//            log.info("Granted Authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());


        } catch (CustomJwtException ex) {
            // 이미 CustomJwtException이면 그대로 ControllerAdvice에서 처리
            throw ex;

        } catch (ExpiredJwtException ex) {
            // 토큰 만료
            // DB에서 사용자 조회
            Map<String, Object> claims = ex.getClaims(); // 만료 토큰에서도 claims 가져오기
            int id = (Integer) claims.get("id");

            User user = userRepository.findByIdWithRole(id)
                    .orElseThrow(() -> new CustomJwtException(JwtErrorCode.INVALID_AUTH));

            // Refresh Token 만료 확인 : JWT토큰이 만료되었습니다.
            if (user.getRefreshTokenExpireAt().isBefore(LocalDateTime.now())) {
                throw new CustomJwtException(JwtErrorCode.EXPIRED_TOKEN);
            }

            // Refresh Token으로 Access Token 갱신
            // 새 클레임 생성 (DB 기반 정보)
            Map<String, Object> newClaims = Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "name", user.getName(),
                    "role", user.getRole().getName()
            );

            // 새 Access Token 발급 (10분 만료)
            String newAccessToken = JwtUtil.generateToken(newClaims, 10);
            log.info("New access token: {}", newAccessToken);

            // 새 토큰을 Response Header에 추가
            response.setHeader("Authorization", "Bearer " + newAccessToken);
            response.setHeader("Access-Control-Expose-Headers", "Authorization"); // 이거 필수!

            // 인증 객체를 SecurityContext에 세팅
            AuthUser authUser = new AuthUser(user.getId(), user.getEmail(), user.getName(), user.getRole().getName());

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authUser, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 필터 체인 계속 진행
            filterChain.doFilter(request, response);


        } catch (Exception ex) {
            // 그 외 JWT 오류
            throw new CustomJwtException(JwtErrorCode.JWT_ERROR);
        }
    }

}
