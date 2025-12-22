package com.mzcteam01.mzcproject01be.domains.meeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateMeetingResponse {

    private int meetingId;
    private String name;
    private String teacherName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int status;
    private String statusName;
    private boolean isOnline;

    public static CreateMeetingResponse success (
                int meetingId,
                String name,
                String teacherName,
                LocalDateTime startAt,
                LocalDateTime endAt,
                boolean isOnline )
    {
        return CreateMeetingResponse.builder()
                .meetingId(meetingId)
                .teacherName(teacherName)
                .startAt(startAt)
                .endAt(endAt)
                .status(0)
                .statusName("대기")
                .isOnline(isOnline)
                .build();
    }
}
