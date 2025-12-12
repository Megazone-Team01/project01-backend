package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetOfflineLectureResponse {
    private int id;
    private String name;
    private int organizationId;
    private String organizationName;
    private String teacherName;
    private String category;
    private int price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private int maxNum;
    private int roomId;
    private String roomName;
    private String startTimeAt;
    private String endTimeAt;
    private String day;

    public static AdminGetOfflineLectureResponse of( OfflineLecture lecture , String day ) {
        return AdminGetOfflineLectureResponse.builder()
                .id(lecture.getId())
                .name( lecture.getName() )
                .organizationId( lecture.getOrganization().getId() )
                .organizationName(lecture.getOrganization().getName() )
                .teacherName( lecture.getTeacher().getName() )
                .category( lecture.getCategory() )
                .price( lecture.getPrice() )
                .startAt( lecture.getStartAt() )
                .endAt( lecture.getEndAt() )
                .description( lecture.getDescription() )
                .maxNum( lecture.getMaxNum() )
                .roomId( lecture.getRoom().getId() )
                .roomName( lecture.getRoom().getName() )
                .startTimeAt( lecture.getStartTimeAt() )
                .endTimeAt( lecture.getEndTimeAt() )
                .day( day )
                .build();
    }
}
