package com.mzcteam01.mzcproject01be.domains.reservation.service;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.request.CreateReseravationRequest;
import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.ReservationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ReservationService {

    void create(int userId, int roomId, LocalDateTime startAt, LocalDateTime endAt);

    public void delete(int id,int deletedBy);

    ReservationResponse createReservation(int userId, CreateReseravationRequest request);

    List<MyReservationListResponse> getMyReservations(int userId, boolean includePast);

    void cancelReservation(int userId, int reservationId);


}
