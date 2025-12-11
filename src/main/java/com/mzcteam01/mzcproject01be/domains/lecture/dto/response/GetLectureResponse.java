package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetLectureResponse {

    private String title;
    private String description;

    public static GetLectureResponse of(Lecture lecture) {
        return GetLectureResponse.builder()
                .title(lecture.getName())
                .description(lecture.getDescription())
                .build();
    }
}
