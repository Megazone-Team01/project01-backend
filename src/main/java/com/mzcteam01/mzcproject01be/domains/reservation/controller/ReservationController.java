package com.mzcteam01.mzcproject01be.domains.reservation.controller;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.request.CreateReseravationRequest;
import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.ReservationResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.service.ReservationServiceImpl;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
@Slf4j
@Tag(name = "Reservation", description = "예약 API")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @PostMapping
    @Operation(summary = "회의실 예약 생성", description = "회의실을 예약함")
    public ResponseEntity<ReservationResponse> createReservation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody CreateReseravationRequest request
    ) {
        int userId = authUser.getId();
        ReservationResponse response = reservationService.createReservation(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/my")
    @Operation( summary = "나의 예약 목록 조회",
            description = "특정 userId값의 예약 목록 조회 " +
                          "includePast=false(기본): 현재 시간 기준으로 예정된 예약만(날짜 빠른순)" +
                          "includePast=true: 과거 예약 목록까지 포함")
    public ResponseEntity<List<MyReservationListResponse>> getMyReservation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(defaultValue = "false") boolean includePast) {

        int userId = authUser.getId();
        List<MyReservationListResponse> reservations = reservationService.getMyReservations(userId, includePast);

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationId}")
    @Operation(summary = "회의실 예약 취소", description = "본인의 회의실 예약을 취소함")
    public ResponseEntity<Map<String, String>> cancelReservation(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable int reservationId
    ) {
        int userId = authUser.getId();
        reservationService.cancelReservation(userId, reservationId);
        return ResponseEntity.ok(Map.of("message", "예약이 취소되었습니다."));
    }
}
