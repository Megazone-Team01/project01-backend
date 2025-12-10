package com.mzcteam01.mzcproject01be.domains.reservation.dto.response;

import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.AdminGetRoomResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetReservationResponse {
    private int id;
    private String userName;
    private int userId;
    private String roomName;
    private int roomId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static AdminGetReservationResponse of( Reservation reservation ) {
        return AdminGetReservationResponse.builder()
                .id(reservation.getId())
                .userName(reservation.getUser().getName())
                .userId( reservation.getUser().getId())
                .roomName( reservation.getRoom().getName() )
                .roomId( reservation.getRoom().getId() )
                .startAt(reservation.getStartAt())
                .endAt(reservation.getEndAt())
                .build();
    }
}
