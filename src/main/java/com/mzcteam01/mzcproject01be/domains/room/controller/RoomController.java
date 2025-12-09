package com.mzcteam01.mzcproject01be.domains.room.controller;

import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    public ResponseEntity<List<RoomListResponse>> getAvailableRooms(@RequestParam Integer organizationId) {

        List<RoomListResponse> rooms = roomService.getAvailableRooms(organizationId);

        return ResponseEntity.ok(rooms);
    }
}
