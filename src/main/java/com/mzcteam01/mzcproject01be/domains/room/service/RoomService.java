package com.mzcteam01.mzcproject01be.domains.room.service;

import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomDetailResponse;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {

    public void create(
            String name,
            int organizationId,
            String location,
            int maxNum,
            LocalDateTime startAt,
            LocalDateTime endAt,
            int managerId,
            RoomStatus status
    );

    public void update(
            int roomId,
            String name,
            String location,
            Integer maxNum,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Integer managerId,
            RoomStatus status
    );

    void delete(int roomId, int deletedBy);

    List<RoomListResponse> getAvailableRooms(int organizationId);

    RoomDetailResponse getRoomDetails(int roomId);
}
