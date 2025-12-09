package com.mzcteam01.mzcproject01be.domains.reservation.service;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<MyReservationListResponse> getMyReservations(Integer userId) {
        log.info("userId: {}", userId);

        return createDummyReservations();
    }

    // 개발용 임시 더미 데이터 생성
    private List<MyReservationListResponse> createDummyReservations() {
        return List.of(
                MyReservationListResponse.builder()
                        .reservationId(1)
                        .roomName("스터디룸 1")
                        .roomLocation("2층 201호")
                        .startAt(LocalDateTime.now().plusDays(1).withHour(14).withMinute(0))
                        .endAt(LocalDateTime.now().plusDays(1).withHour(16).withMinute(0))
                        .organizationName("메가존클라우드 교육센터")
                        .build(),
                MyReservationListResponse.builder()
                        .reservationId(2)
                        .roomName("스터디룸 2")
                        .roomLocation("2층 202호")
                        .startAt(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0))
                        .endAt(LocalDateTime.now().plusDays(2).withHour(11).withMinute(0))
                        .organizationName("메가존클라우드 교육센터")
                        .build()
        );
    }
}
