package com.mzcteam01.mzcproject01be.domains.reservation.repository;

import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
