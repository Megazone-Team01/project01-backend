package com.mzcteam01.mzcproject01be.domains.room.dto.response;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDetailResponse {

    private Integer id;
    private Integer organizationId;
    private String name;
    private String organizationName;
    private String location;
    private Integer maxNum;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private RoomStatus status;
    // + 관리자 정보 ?

    public static RoomDetailResponse from(Room room) {
        return RoomDetailResponse.builder()
                .id(room.getId())
                .organizationId(room.getOrganization().getId())
                .name(room.getName())
                .organizationName(room.getOrganization().getName())
                .location(room.getLocation())
                .maxNum(room.getMaxNum())
                .startAt(room.getStartAt())
                .endAt(room.getEndAt())
                .status(room.getStatus())
                .build();
    }
}