료package com.mzcteam01.mzcproject01be.domains.reservation.dto.response;

import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class ReservationResponse {
    private int reservationId;
    private int roomId;
    private String roomName;
    private String roomLocation;
    private String organizationName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;

    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .roomName(reservation.getRoom().getName())
                .roomLocation(reservation.getRoom().getLocation())
                .organizationName(reservation.getRoom().getOrganization().getName())
                .startAt(reservation.getStartAt())
                .endAt(reservation.getEndAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
