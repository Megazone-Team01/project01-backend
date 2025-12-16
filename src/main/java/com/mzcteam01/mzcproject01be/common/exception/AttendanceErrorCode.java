package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendanceErrorCode {
    ATTENDANCE_NOT_FOUND( "해당 출석이 존재하지 않습니다" );

    private String message;
}
