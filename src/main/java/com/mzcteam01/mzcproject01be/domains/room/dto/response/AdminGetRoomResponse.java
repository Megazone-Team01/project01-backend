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
public class AdminGetRoomResponse {
    private int id;
    private String name;
    private String organizationName;
    private String location;
    private int maxNum;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String managerName;
    private int managerId;
    private RoomStatus status;

    public static AdminGetRoomResponse of(Room room){
        return AdminGetRoomResponse.builder()
                .id( room.getId() )
                .name( room.getName() )
                .organizationName( room.getOrganization().getName() )
                .location( room.getLocation() )
                .maxNum( room.getMaxNum() )
                .startAt( room.getStartAt() )
                .endAt( room.getEndAt() )
                .managerName( room.getManager().getName() )
                .managerId( room.getManager().getId() )
                .status( room.getStatus() )
                .build();
    }
}
