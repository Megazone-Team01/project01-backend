package com.mzcteam01.mzcproject01be.domains.lecture.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateLectureRequest {
    private int isOnline;
    private String name;
    private Integer price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private Integer maxNum;
    private List<String> days;
    private String startTimeAt;
    private String endTimeAt;
}
