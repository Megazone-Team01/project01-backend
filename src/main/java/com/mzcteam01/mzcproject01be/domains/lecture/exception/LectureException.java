package com.mzcteam01.mzcproject01be.domains.lecture.exception;


import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;

public class LectureException extends RuntimeException {
    private final LectureErrorCode errorCode;

    public LectureException(LectureErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LectureErrorCode getErrorCode() {
        return errorCode;
    }
}