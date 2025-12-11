package com.mzcteam01.mzcproject01be.domains.room.controller;

import com.mzcteam01.mzcproject01be.common.enums.RoomErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomDetailResponse;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomListResponse>> getAvailableRooms(@RequestParam Integer organizationId) {

        List<RoomListResponse> rooms = roomService.getAvailableRooms(organizationId);

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailResponse> getRoomDetails(@PathVariable Integer roomId) {

        log.info("roomId {}번 상세조회", roomId);

        try{
            RoomDetailResponse room = roomService.getRoomDetails(roomId);
            return ResponseEntity.ok(room);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
