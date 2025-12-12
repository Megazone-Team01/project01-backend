package com.mzcteam01.mzcproject01be.domains.meeting.dto.response;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetMeetingResponse {
    private int id;
    private boolean isOnline;
    private String name;
    private int organizationId;
    private String organizationName;
    private int teacherId;
    private String teacherName;
    private int studentId;
    private String studentName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int status;
    private String location;

    public static AdminGetMeetingResponse of( Meeting meeting, boolean isOnline, String location ) {
        return AdminGetMeetingResponse.builder()
                .id(meeting.getId())
                .isOnline( isOnline )
                .name( meeting.getName() )
                .organizationId( meeting.getOrganization().getId() )
                .organizationName( meeting.getOrganization().getName() )
                .teacherId( meeting.getTeacher().getId() )
                .teacherName( meeting.getTeacher().getName() )
                .studentId( meeting.getStudent().getId() )
                .studentName( meeting.getStudent().getName() )
                .startAt( meeting.getStartAt() )
                .endAt( meeting.getEndAt() )
                .status( meeting.getStatus() )
                .location( location )
                .build();
    }
}
