package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureOnlineDetailResponse {
    private String name;
    private String description;
    private int price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int status;

    public static LectureOnlineDetailResponse of(OnlineLecture online){
        return LectureOnlineDetailResponse.builder()
                .name(online.getName())
                .description(online.getDescription())
                .price(online.getPrice())
                .startAt(online.getStartAt())
                .endAt(online.getEndAt())
                .status(online.getStatus())
                .build();
    }

}
