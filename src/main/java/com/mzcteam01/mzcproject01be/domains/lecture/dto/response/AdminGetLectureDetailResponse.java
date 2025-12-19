package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetLectureDetailResponse {
    private int id;
    private boolean isOnline;
    private String name;
    private String organizationName;
    private String teacherName;
    private List<String> category;
    private int price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private Integer maxNum;
    private String roomName;
    private String startTimeAt;
    private String endTimeAt;
    private String day;
    private Integer status;
    private Integer fileId;
    private String fileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static AdminGetLectureDetailResponse ofOnline( OnlineLecture lecture, List<String> categoryLayer ){
        return AdminGetLectureDetailResponse.builder()
                .id( lecture.getId() )
                .isOnline( true )
                .name( lecture.getName() )
                .organizationName( lecture.getOrganization().getName() )
                .teacherName( lecture.getTeacher().getName() )
                .category(categoryLayer)
                .price(lecture.getPrice())
                .startAt(lecture.getStartAt())
                .endAt(lecture.getEndAt())
                .description(lecture.getDescription())
                .fileId(lecture.getFile().getId())
                .fileUrl(lecture.getFile().getUrl())
                .status(lecture.getStatus())
                .createdAt(lecture.getCreatedAt())
                .updatedAt(lecture.getUpdatedAt())
                .deletedAt(lecture.getDeletedAt())
                .build();
    }
    public static AdminGetLectureDetailResponse ofOffline( OfflineLecture lecture, String day, List<String> categoryLayer  ){
        return AdminGetLectureDetailResponse.builder()
                .id( lecture.getId() )
                .isOnline( false )
                .name( lecture.getName() )
                .organizationName( lecture.getOrganization().getName() )
                .teacherName( lecture.getTeacher().getName() )
                .category(categoryLayer)
                .price(lecture.getPrice())
                .startAt(lecture.getStartAt())
                .endAt(lecture.getEndAt())
                .description(lecture.getDescription())
                .maxNum( lecture.getMaxNum() )
                .roomName( lecture.getRoom().getName() )
                .startTimeAt(lecture.getStartTimeAt())
                .endTimeAt(lecture.getEndTimeAt())
                .createdAt(lecture.getCreatedAt())
                .updatedAt(lecture.getUpdatedAt())
                .deletedAt(lecture.getDeletedAt())
                .day( day )
                .build();
    }
}
