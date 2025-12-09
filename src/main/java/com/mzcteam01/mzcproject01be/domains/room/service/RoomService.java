package com.mzcteam01.mzcproject01be.domains.room.service;

import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomListResponse> getAvailableRooms(Integer organizationId) {

        List<Room> rooms = roomRepository.findByOrganizationIdAndStatus(organizationId, RoomStatus.AVAILABLE);

        return rooms.stream()
                .map(RoomListResponse::from)
                .collect(Collectors.toList());
    }
}
