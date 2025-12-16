package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayErrorCode {
    DAY_NOT_FOUND("요일이 존재하지 않습니다"),
    DAY_VALUE_ALREADY_EXIST("해당 요일 코드가 이미 존재합니다"),
    INVALID_DAY_ACCESS( "잘못된 접근입니다" );

    private String message;
}
