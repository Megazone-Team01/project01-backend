package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

@Getter
public enum ReservationErrorCode {

    RESERVATION_NOT_FOUND("존재하지 않는 예약입니다");

    private final String message;

    ReservationErrorCode(String message) {
        this.message = message;
    }
}
