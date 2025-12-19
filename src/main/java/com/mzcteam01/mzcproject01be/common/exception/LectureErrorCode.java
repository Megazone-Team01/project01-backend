package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

@Getter
public enum LectureErrorCode {
    OFFLINE_NOT_FOUND("존재하지 않는 강의실입니다!"),
    ONLINE_NOT_FOUND("존재하지 않는 온라인강의입니다!"), 
    LECTURE_TYPE_ERROR("강의 유형이 잘못되었습니다");

    private final String message;

    LectureErrorCode(String message) {
        this.message = message;
    }

}
