package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

@Getter
public enum MeetingErrorCode {
    MEETING_NOT_FOUND( "존재하지 않는 면담입니다" ),
    TIME_SLOT_NOT_AVAILABLE("이미 예약된 시간대입니다."),
    LOCATION_REQUIRED("온라인 상담 승인 시 화상회의 링크는 필수입니다."),
    NOT_YOUR_MEETING("본인의 상담만 취소 가능합니다."),
    CANNOT_CANCEL_APPROVED("승인된 상담은 취소할 수 없습니다. 관리자에게 문의하세요.");


    private String message;

    MeetingErrorCode(String message) {
        this.message = message;
    }
}
