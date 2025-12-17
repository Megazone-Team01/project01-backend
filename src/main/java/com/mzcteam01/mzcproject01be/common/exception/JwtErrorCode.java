package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

public enum JwtErrorCode {
    MALFORMED_TOKEN("JWT 형식이 잘못되었습니다.", 400),
    EXPIRED_TOKEN("JWT 토큰이 만료되었습니다.", 401),
    INVALID_CLAIM("JWT claim 정보가 유효하지 않습니다.", 400),
    JWT_ERROR("JWT 서명 검증에 실패했습니다.", 401),
    GENERAL_ERROR("JWT 처리 중 오류가 발생했습니다.", 500),
    ACCESS_DENIED("권한이 없습니다.", 403),
    MISSING_TOKEN("JWT 토큰이 존재하지 않습니다.", 401),
    INVALID_AUTH("인증에 실패하였습니다.", 401);

    private final String message;
    private final int status;

    JwtErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() { return message; }
    public int getStatus() { return status; }
}