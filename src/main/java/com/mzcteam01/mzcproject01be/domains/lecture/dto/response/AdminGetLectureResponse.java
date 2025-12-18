package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetLectureResponse {
    private int id;
    private boolean isOnline;
    private String name;
    private int organizationId;
    private String organizationName;
    private String teacherName;
    private String category;
    private int price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private LocalDateTime createdAt;

    public static AdminGetLectureResponse of(Lecture lecture, boolean isOnline) {
        return AdminGetLectureResponse.builder()
                .id( lecture.getId() )
                .isOnline( isOnline )
                .name( lecture.getName() )
                .organizationId( lecture.getOrganization().getId() )
                .organizationName( lecture.getOrganization().getName() )
                .teacherName( lecture.getTeacher().getName() )
                .category( lecture.getCategory() )
                .price( lecture.getPrice() )
                .startAt( lecture.getStartAt() )
                .endAt( lecture.getEndAt() )
                .description(lecture.getDescription())
                .createdAt(lecture.getCreatedAt())
                .build();
    }
}
