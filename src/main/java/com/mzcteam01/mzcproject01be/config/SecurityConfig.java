package com.mzcteam01.mzcproject01be.config;

import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import com.mzcteam01.mzcproject01be.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userRoleRepository, userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 세션 Stateless 적용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // URL 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/user/sign", "/api/v1/user/login", "/api/v1/refresh").permitAll()
                        .anyRequest().authenticated()
                )

                // JWT 필터 등록 (추가)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private String[] getPermitUrls() {
        return new String[]{
                "/ping",
                "/swagger-ui/**",
                "/api-docs/**"
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of(
                        // NOTE: 로컬 개발 허용(정규식?)
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://localhost:3002",
                        "http://localhost:3003",
                        "http://localhost:5173"
                ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "OPTIONS", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
