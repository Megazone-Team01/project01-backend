package com.mzcteam01.mzcproject01be.domains.reservation.controller;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
@Slf4j
@Tag(name = "Reservation", description = "예약 API")
public class ReservationController {

    private final ReservationService reservationService;

    // TODO : JWT 인증 구현 후, SecurityContext에서 사용자 ID 추출

    @GetMapping("/my")
    @Operation( summary = "나의 예약 목록 조회",
            description = "특정 userId값의 예약 목록 조회 " +
                          "includePast=false(기본): 현재 시간 기준으로 예정된 예약만(날짜 빠른순)" +
                          "includePast=true: 과거 예약 목록까지 포함")
    public ResponseEntity<List<MyReservationListResponse>> getMyReservation(
                                                            @RequestParam Integer userId,
                                                            @RequestParam(defaultValue = "false") boolean includePast) {
        List<MyReservationListResponse> reservations = reservationService.getMyReservations(userId, includePast);

        return ResponseEntity.ok(reservations);
    }

}
