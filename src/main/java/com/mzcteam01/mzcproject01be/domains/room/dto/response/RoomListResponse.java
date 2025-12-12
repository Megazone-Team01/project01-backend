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
public class RoomListResponse {

    private Integer id;
    private String name;
    private String location;
    private Integer maxNum;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private RoomStatus status;
    private String managerName;

    public static RoomListResponse from(Room room) {
        return RoomListResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .location(room.getLocation())
                .maxNum(room.getMaxNum())
                .startAt(room.getStartAt())
                .endAt(room.getEndAt())
                .status(room.getStatus())
                .managerName(room.getManager().getName())
                .build();
    }

}
