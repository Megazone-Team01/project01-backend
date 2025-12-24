package com.mzcteam01.mzcproject01be.domains.file.dto.response;

import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    private Integer fileId;
    private Integer lectureId;
    private String originalName;
    private String url;
    private String title;
    private String teacherName;
    private String description;

    public static FileResponse of(File file, OnlineLecture lecture) {
        return FileResponse.builder()
                .fileId( file.getId() )
                .lectureId( lecture.getId() )
                .originalName( file.getOriginalName() )
                .url( file.getUrl() )
                .title( lecture.getName() )
                .teacherName( lecture.getTeacher().getName() )
                .description( lecture.getDescription() )
                .build();

    }
}
