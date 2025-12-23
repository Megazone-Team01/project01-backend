package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder(toBuilder = true)
public class LectureOfflineDetailResponse {
    private String name;
    private String description;
    private String teacherName;
    private String organizationName;
    private String organizationDescription;
    private List<String> category;
    private int price;
    private int day;
    private int maxNum;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean exists;
    private String thumbnail;

    public static LectureOfflineDetailResponse of(OfflineLecture offline, List<String> categoryLayer ){
        return LectureOfflineDetailResponse.builder()
                .name(offline.getName())
                .teacherName(offline.getTeacher().getName())
                .organizationName(offline.getOrganization().getName())
                .organizationDescription(offline.getOrganization().getDescription())
                .thumbnail(offline.getThumbnailFile() != null ? offline.getThumbnailFile().getUrl() : null)
                .day(offline.getDay())
                .category( categoryLayer )
                .maxNum(offline.getMaxNum())
                .description(offline.getDescription())
                .price(offline.getPrice())
                .startAt(offline.getStartAt())
                .endAt(offline.getEndAt())
                .build();
    }

}
