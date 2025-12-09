package com.mzcteam01.mzcproject01be.domains.reservation.controller;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.service.ReservationService;
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
@RequestMapping("/api/reservations")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    // TODO : JWT 인증 구현 후, SecurityContext에서 사용자 ID 추출

    @GetMapping("/my")
    public ResponseEntity<List<MyReservationListResponse>> getMyReservation(@RequestParam Integer userId) {

        List<MyReservationListResponse> reservations = reservationService.getMyReservations(userId);

        return ResponseEntity.ok(reservations);
    }

}
