package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureOfflineDetailResponse {
    private String name;
    private String description;
    private int price;
    private int day;
    private int max_num;
    private String start_time;
    private String end_time;

    public static LectureOfflineDetailResponse of(OfflineLecture  offline){
        return LectureOfflineDetailResponse.builder()
                .name(offline.getName())
                .description(offline.getDescription())
                .price(offline.getPrice())
                .day(offline.getDay())
                .max_num(offline.getMaxNum())
                .start_time(offline.getStartTimeAt())
                .end_time(offline.getEndTimeAt())
                .build();
    }

}
