package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingErrorCode {
    MEETING_NOT_FOUND( "존재하지 않는 면담입니다" );

    private String message;
}
