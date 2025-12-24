package com.mzcteam01.mzcproject01be.domains.meeting.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApproveMeetingRequest {
    private String location;

    /**
     * 오프라인 상담 승인 과정에서
     * '강사가 학생이 신청한 예약 장소가 부적합하다고 판단되는 경우 장소를 수정할 수 있어야한다'
     * 라는 요구사항이 추가될시에 고민
     */
    // private Integer roomId;
}
