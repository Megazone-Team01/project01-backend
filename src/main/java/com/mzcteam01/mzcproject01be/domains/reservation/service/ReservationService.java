package com.mzcteam01.mzcproject01be.domains.reservation.service;

import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.MyReservationListResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import com.mzcteam01.mzcproject01be.domains.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<MyReservationListResponse> getMyReservations(Integer userId, boolean includePast) {

        LocalDateTime now = LocalDateTime.now();
        List<Reservation> myReservations = reservationRepository.findMyReservations(userId, now);

        List<MyReservationListResponse> responses = new ArrayList<>();

        for (Reservation reservation : myReservations) {
            responses.add(MyReservationListResponse.from(reservation));
        }

        if (includePast){
            List<Reservation> pastReservations = reservationRepository.findPastReservations(userId, now);

            for (Reservation reservation : pastReservations) {
                responses.add(MyReservationListResponse.from(reservation));
            }
        }

        return responses;
    }
}
