package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LectureErrorCode {
    OFFLINE_NOT_FOUND("존재하지 않는 강의입니다!", HttpStatus.NOT_FOUND),
    ONLINE_NOT_FOUND("존재하지 않는 온라인강의입니다!",HttpStatus.NOT_FOUND),
    LECTURE_TYPE_ERROR("강의 유형이 잘못되었습니다", HttpStatus.BAD_REQUEST),
    STATUS_NOT_APPROVED("승인되지 않았습니다. 기관에 문의하십시오!",HttpStatus.FORBIDDEN),
    APPLIED_LECTURE("이미 신청한 강의입니다!",HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    LectureErrorCode(String message, HttpStatus httpStatus) {
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

}
