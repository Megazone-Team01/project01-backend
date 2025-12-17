package com.mzcteam01.mzcproject01be.security.handler;

import com.google.gson.Gson;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class ApiAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        AuthUser auditUser = (AuthUser) authentication.getPrincipal();

        Map<String, Object> claims = auditUser.getClaims();

        // Access Token, Refresh Token 생성
        String accessToken = JwtUtil.generateToken(claims, 10);
        String refreshToken = JwtUtil.generateToken(claims, 60 * 24 * 180);  // 180일

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(jsonStr);
        pw.close();
    }
}