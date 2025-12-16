package com.mzcteam01.mzcproject01be.domains.organization.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOrganizationLectureResponse {
    private int id;
    private String name;
    private String teacherName;
    private String description;

    public static GetOrganizationLectureResponse of(Lecture lecture){
        return GetOrganizationLectureResponse.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .teacherName(lecture.getTeacher().getName())
                .description(lecture.getDescription())
                .build();
    }
}
