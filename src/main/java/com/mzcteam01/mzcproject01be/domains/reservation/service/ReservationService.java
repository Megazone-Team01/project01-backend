package com.mzcteam01.mzcproject01be.domains.reservation.service;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    void create(int userId, int roomId, LocalDateTime startAt, LocalDateTime endAt);

    List<MyReservationListResponse> getMyReservations(int userId, boolean includePast);

    public void delete(int id,int deletedBy);
}
