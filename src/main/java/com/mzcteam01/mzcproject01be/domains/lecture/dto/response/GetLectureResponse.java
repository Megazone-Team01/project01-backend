package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetLectureResponse {

    private int id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String thumbnail;

    public static GetLectureResponse of(Lecture lecture) {
        return GetLectureResponse.builder()
                .id(lecture.getId())
                .thumbnail(lecture.getThumbnailFile() != null ? lecture.getThumbnailFile().getUrl() : null)
                .title(lecture.getName())
                .description(lecture.getDescription())
                .startTime(lecture.getStartAt())
                .endTime(lecture.getEndAt())
                .build();
    }
}
