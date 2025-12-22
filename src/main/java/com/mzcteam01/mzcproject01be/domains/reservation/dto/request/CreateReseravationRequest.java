package com.mzcteam01.mzcproject01be.domains.reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateReseravationRequest {

    private int roomId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

}
