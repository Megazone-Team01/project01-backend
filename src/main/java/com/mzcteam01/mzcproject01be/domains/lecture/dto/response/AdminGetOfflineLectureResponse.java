package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> category;
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

    public static AdminGetOfflineLectureResponse of( OfflineLecture lecture , String day, List<String> categoryLayer  ) {
        return AdminGetOfflineLectureResponse.builder()
                .id(lecture.getId())
                .name( lecture.getName() )
                .organizationId( lecture.getOrganization().getId() )
                .organizationName(lecture.getOrganization().getName() )
                .teacherName( lecture.getTeacher().getName() )
                .category( categoryLayer )
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
