package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {
    USER_NOT_FOUND("해당하는 유저를 찾을 수 없습니다", "USER_NOT_FOUND", HttpStatus.NOT_FOUND),
    TEACHER_NOT_FOUND("해당하는 선생님을 찾을 수 없습니다.", "TEACHER_NOT_FOUND", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.", "PASSWORD_NOT_MATCH", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", "EMAIL_ALREADY_EXISTS", HttpStatus.CONFLICT),
    EMAIL_NOT_FOUND("존재하지 않는 이메일입니다.", "EMAIL_NOT_FOUND", HttpStatus.NOT_FOUND),
    DEFAULT_ROLE_NOT_FOUND("기본 사용자 권한을 찾을 수 없습니다.", "DEFAULT_ROLE_NOT_FOUND", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_NOT_FOUND("사용자 역할을 찾을 수 없습니다.", "ROLE_NOT_FOUND", HttpStatus.NOT_FOUND),
    USER_ALREADY_APPLIED("이미 정보가 존재합니다", "USER_ALREADY_APPLIED", HttpStatus.CONFLICT),
    LOGIN_FAILED("로그인에 실패하였습니다.", "LOGIN_FAILED", HttpStatus.UNAUTHORIZED);

    private final String message;   // 좌측: 사용자에게 보여줄 메시지
    private final String code;      // 우측: 내부 코드
    private final HttpStatus status; // HTTP 상태 코드

    UserErrorCode(String message, String code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}