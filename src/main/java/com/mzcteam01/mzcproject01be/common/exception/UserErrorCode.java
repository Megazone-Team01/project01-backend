package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

@Getter
public enum UserErrorCode {
    USER_NOT_FOUND( "해당하는 유저를 찾을 수 없습니다" ),
    TEACHER_NOT_FOUND("해당하는 선생님을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다."),
    EMAIL_NOT_FOUND("존재하지 않는 이메일입니다."),
    DEFAULT_ROLE_NOT_FOUND("기본 사용자 권한을 찾을 수 없습니다."),
    LOGIN_FAILED("로그인에 실패하였습니다.");

    private final String message;

    UserErrorCode(String message) {
        this.message = message;
    }
}
