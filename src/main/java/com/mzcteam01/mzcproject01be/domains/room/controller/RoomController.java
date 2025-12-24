package com.mzcteam01.mzcproject01be.domains.room.controller;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomDetailResponse;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.room.service.RoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Room", description = "회의실 API")
public class RoomController {

    private final RoomServiceImpl roomService;

    @GetMapping
    @Operation( summary = "사용가능한 회의실 목록 조회",
                description = "organizationId 와 roomStatus(AVAILABLE)로 조회, 이름순으로 정렬됨")
    public ResponseEntity<List<RoomListResponse>> getAvailableRooms(@RequestParam Integer organizationId) {

        List<RoomListResponse> rooms = roomService.getAvailableRooms(organizationId);

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    @Operation( summary = "회의실 상세내용 조회",
            description = "특정 회의실의 상세 정보(관리자 정보, 기관 정보)를 포함")
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
