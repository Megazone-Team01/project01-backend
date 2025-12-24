package com.mzcteam01.mzcproject01be.common.advice;

import com.mzcteam01.mzcproject01be.common.exception.JwtException;
import com.mzcteam01.mzcproject01be.common.exception.JwtErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;

@RestControllerAdvice
public class JwtExceptionAdvice {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException ex) {
        JwtErrorCode code = ex.getErrorCode();
        return ResponseEntity.status(code.getStatus())
                .body(new ErrorResponse(code.name(), code.getMessage()));
    }

    // CustomException 처리 추가
    @ExceptionHandler(com.mzcteam01.mzcproject01be.common.exception.CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        // 상태 코드를 예로 401 Unauthorized로 설정 (로그인 실패)
        return ResponseEntity.status(401)
                .body(new ErrorResponse("LOGIN_FAILED", ex.getMessage()));
    }

    // ErrorResponse DTO
    static class ErrorResponse {
        private final String code;
        private final String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}