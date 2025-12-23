package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

@Getter
public enum ReservationErrorCode {

    RESERVATION_NOT_FOUND("존재하지 않는 예약입니다"),
    ROOM_NOT_AVAILABLE("해당 회의실은 현재 사용할 수 없습니다."),
    TIME_SLOT_ALREADY_BOOKED("해당 시간대는 이미 예약되어 있습니다."),
    INVALID_TIME_RANGE("시작 시간이 종료 시간보다 늦을 수 없습니다."),
    PAST_TIME_NOT_ALLOWED("과거 시간에는 예약할 수 없습니다."),
    NOT_YOUR_RESERVATION("본인의 예약만 취소할 수 있습니다."),
    ALREADY_CANCELLED("이미 취소된 예약입니다."),
    CANNOT_CANCEL_PAST_RESERVATION("이미 지난 예약은 취소할 수 없습니다.");

    private final String message;

    ReservationErrorCode(String message) {
        this.message = message;
    }
}
