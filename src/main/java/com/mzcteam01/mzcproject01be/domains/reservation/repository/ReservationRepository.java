package com.mzcteam01.mzcproject01be.domains.reservation.repository;

import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByUserId( int userId );

}
