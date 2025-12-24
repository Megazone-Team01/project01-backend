package com.mzcteam01.mzcproject01be.domains.attendence.dto.response;

import com.mzcteam01.mzcproject01be.domains.attendence.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetAttendanceResponse {
    private int id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int organizationId;
    private String organizationName;
    private int userId;
    private String userName;

    public static AdminGetAttendanceResponse of(Attendance attendance) {
        return AdminGetAttendanceResponse.builder()
                .id( attendance.getId() )
                .checkIn( attendance.getCheckIn() )
                .checkOut( attendance.getCheckOut() )
                .organizationId( attendance.getOrganization().getId() )
                .organizationName( attendance.getOrganization().getName() )
                .userId( attendance.getUser().getId() )
                .userName( attendance.getUser().getName() )
                .build();
    }
}
