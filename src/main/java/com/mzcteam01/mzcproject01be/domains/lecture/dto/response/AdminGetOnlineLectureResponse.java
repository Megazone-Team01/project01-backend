package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetOnlineLectureResponse {
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
    private int fileId;
    private String fileUrl;
    private String status;

    public static AdminGetOnlineLectureResponse of( OnlineLecture lecture ) {
        return AdminGetOnlineLectureResponse.builder()
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
                .fileId( lecture.getFile().getId() )
                .fileUrl( lecture.getFile().getUrl() )
                .status( lecture.getStatus() == 0 ? "대기중" : lecture.getStatus() == 1 ? "승인" : "반려" )
                .build();
    }
}
