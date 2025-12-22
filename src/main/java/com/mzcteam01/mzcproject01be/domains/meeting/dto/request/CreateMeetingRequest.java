package com.mzcteam01.mzcproject01be.domains.meeting.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateMeetingRequest {

    private boolean isOnline;
    private String name; // 상담 유형 (상담 카테고리 드롭다운 선택값)
    private int organizationId;
    private int teacherId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String location;
    private Integer roomId;

}
