package com.mzcteam01.mzcproject01be.domains.meeting.dto.response;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyMeetingListResponse {

    private int meetingId;
    private String name;
    private String teacherName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int status;           // -1: 반려, 0: 대기, 1: 승인, 2: 완료
    private String statusName;    // 프론트에서 문자열로 바로 사용하게끔 추가
    private boolean isOnline;
    private String location;      // 온라인이면 온라인 링크, 오프라인이면 room 위치 반환

    public static MyMeetingListResponse fromOnline(OnlineMeeting meeting) {
        return MyMeetingListResponse.builder()
                .meetingId(meeting.getId())
                .name(meeting.getName())
                .teacherName(meeting.getTeacher().getName())
                .startAt(meeting.getStartAt())
                .endAt(meeting.getEndAt())
                .status(meeting.getStatus())
                .statusName(getStatusName(meeting.getStatus()))
                .isOnline(true)
                .location(meeting.getLocation())
                .build();
    }

    public static MyMeetingListResponse fromOffline(OfflineMeeting meeting) {
        return MyMeetingListResponse.builder()
                .meetingId(meeting.getId())
                .name(meeting.getName())
                .teacherName(meeting.getTeacher().getName())
                .startAt(meeting.getStartAt())
                .endAt(meeting.getEndAt())
                .status(meeting.getStatus())
                .statusName(getStatusName(meeting.getStatus()))
                .isOnline(false)
                .location(meeting.getRoom() != null ? meeting.getRoom().getName() : null)
                .build();
    }

    private static String getStatusName(int status) {
        return switch (status) {
            case -1 -> "반려";
            case 0 -> "대기";
            case 1 -> "승인";
            case 2 -> "완료";
            default -> "알수없음";
        };
    }
}
