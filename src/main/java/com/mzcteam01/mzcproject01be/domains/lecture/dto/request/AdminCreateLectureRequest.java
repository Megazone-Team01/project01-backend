package com.mzcteam01.mzcproject01be.domains.lecture.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateLectureRequest {
    private String name;
    private Integer organizationId;
    private Integer teacherId;
    private String category;
    private String description;
    private Integer type;
    private Integer fileId;
    private Integer maxNum;
    private Integer price;
    private Integer roomId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String startTimeAt;
    private String endTimeAt;
    private List<String> dayValue;
    private Integer thumbnailId;
}
