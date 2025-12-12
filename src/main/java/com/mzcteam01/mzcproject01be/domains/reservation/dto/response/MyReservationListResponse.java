package com.mzcteam01.mzcproject01be.domains.reservation.dto.response;

import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyReservationListResponse {

    private Integer reservationId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
    private Integer roomId;
    private String roomName;
    private String roomLocation;
    private Integer maxNum;
    private RoomStatus roomStatus;
    private String organizationName;

    public static MyReservationListResponse from(Reservation reservation) {
        return MyReservationListResponse.builder()
                .reservationId(reservation.getId())
                .startAt(reservation.getStartAt())
                .endAt(reservation.getEndAt())
                .createdAt(reservation.getCreatedAt())
                .roomId(reservation.getRoom().getId())
                .roomName(reservation.getRoom().getName())
                .roomLocation(reservation.getRoom().getLocation())
                .maxNum(reservation.getRoom().getMaxNum())
                .roomStatus(reservation.getRoom().getStatus())
                .organizationName(reservation.getRoom().getOrganization().getName())
                .build();
    }
}






