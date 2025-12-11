package com.mzcteam01.mzcproject01be.domains.lecture.enums;

import lombok.Getter;

@Getter
public enum LectureErrorCode {
    OFFLINE_NOT_FOUND("존재하지 않는 강의실입니다!"),
    ONLINE_NOT_FOUND("존재하지 않는 온라인강의입니다!");

    private final String message;

    LectureErrorCode(String message) {
        this.message = message;
    }

}
