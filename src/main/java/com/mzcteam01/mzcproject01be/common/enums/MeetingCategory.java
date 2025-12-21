package com.mzcteam01.mzcproject01be.common.enums;

public enum MeetingCategory {
    CAREER("진로 상담"),
    LECTURE("강의 관련 문의"),
    STUDY("학습 방법 상담"),
    SCHEDULE("일정 조율"),
    OTHER("기타");

    private final String description;

    MeetingCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
