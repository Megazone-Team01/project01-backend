package com.mzcteam01.mzcproject01be.domains.reservation.repository;

import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // 각 사용자의 예약 목록 조회 - 조회시점에서 가까운 일정순으로
    @Query( """
            SELECT r 
            FROM Reservation r
            JOIN FETCH r.room room
            JOIN FETCH room.organization
            WHERE r.user.id = :userId
                AND r.startAt >= :now
                AND r.deletedAt IS NULL
            ORDER BY r.startAt ASC 
            """)
    List<Reservation> findMyReservations(Integer userId, LocalDateTime now);


    // 지난 예약 조회 (오래된 순서대로)

}
