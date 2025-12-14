package com.mzcteam01.mzcproject01be.security.handler;

import com.google.gson.Gson;

import com.mzcteam01.mzcproject01be.common.exception.JwtErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class CustomAccessDeniedHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        Gson gson = new Gson();

        JwtErrorCode errorCode = JwtErrorCode.ACCESS_DENIED;

        String jsonStr = gson.toJson(Map.of("ERROR", errorCode.getMessage()));

        response.setContentType("application/json;charset=UTF-8");

        PrintWriter pw = response.getWriter();
        pw.println(jsonStr);
        pw.close();
    }
}
