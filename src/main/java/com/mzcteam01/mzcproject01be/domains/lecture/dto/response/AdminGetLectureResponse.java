package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
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
public class AdminGetLectureResponse {
    private int id;
    private Boolean isOnline;
    private String name;
    private int organizationId;
    private String organizationName;
    private String teacherName;
    private List<String> category;
    private int price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private String thumbnail;

    public static AdminGetLectureResponse of(Lecture lecture, Boolean isOnline, List<String> categoryLayer ) {
        return AdminGetLectureResponse.builder()
                .id( lecture.getId() )
                .isOnline( isOnline )
                .name( lecture.getName() )
                .organizationId( lecture.getOrganization().getId() )
                .organizationName( lecture.getOrganization().getName() )
                .teacherName( lecture.getTeacher().getName() )
                .category( categoryLayer )
                .price( lecture.getPrice() )
                .startAt( lecture.getStartAt() )
                .endAt( lecture.getEndAt() )
                .thumbnail( lecture.getThumbnail() != null ? lecture.getThumbnail().getUrl() : null  )
                .description(lecture.getDescription())
                .createdAt(lecture.getCreatedAt())
                .deletedAt(lecture.getDeletedAt())
                .build();
    }
}
