package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureOfflineDetailResponse {
    private String name;
    private String description;
    private String teacherName;
    private String organizationName;
    private String organizationDescription;
    private String category;
    private int price;
    private int day;
    private int maxNum;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static LectureOfflineDetailResponse of(OfflineLecture offline){
        return LectureOfflineDetailResponse.builder()
                .name(offline.getName())
                .teacherName(offline.getTeacher().getName())
                .organizationName(offline.getOrganization().getName())
                .organizationDescription(offline.getOrganization().getDescription())
                .day(offline.getDay())
                .category(offline.getCategory())
                .maxNum(offline.getMaxNum())
                .description(offline.getDescription())
                .price(offline.getPrice())
                .startAt(offline.getStartAt())
                .endAt(offline.getEndAt())
                .build();
    }

}
