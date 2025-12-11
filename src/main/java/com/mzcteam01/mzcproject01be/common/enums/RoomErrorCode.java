package com.mzcteam01.mzcproject01be.common.enums;

import lombok.Getter;

@Getter
public enum RoomErrorCode {

    ROOM_NOT_FOUND("존재하지 않는 회의실입니다"),
    ROOM_NOT_AVAILABLE("사용할 수 없는 스터디룸입니다");

    private final String message;

    RoomErrorCode(String message) {
        this.message = message;
    }
}
