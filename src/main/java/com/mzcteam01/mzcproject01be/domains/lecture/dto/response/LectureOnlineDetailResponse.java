package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Setter
public class LectureOnlineDetailResponse {
    private String name;
    private String teacherName;
    private  String organizationName;
    private String organizationDescription;
    private List<String> category;
    private String description;
    private int price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int status;
    private boolean exists;
    private String thumbnail;

    public static LectureOnlineDetailResponse of(OnlineLecture online, List<String> categoryLayer ){
        return LectureOnlineDetailResponse.builder()
                .name(online.getName())
                .teacherName(online.getTeacher().getName())
                .thumbnail(online.getThumbnailFile() != null ? online.getThumbnailFile().getUrl() : null)
                .organizationName(online.getOrganization().getName())
                .organizationDescription(online.getOrganization().getDescription())
                .description(online.getDescription())
                .category( categoryLayer )
                .price(online.getPrice())
                .startAt(online.getStartAt())
                .endAt(online.getEndAt())
                .status(online.getStatus())
                .build();
    }

}
