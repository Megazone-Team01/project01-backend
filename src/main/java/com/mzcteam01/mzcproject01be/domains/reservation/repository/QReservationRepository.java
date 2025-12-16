package com.mzcteam01.mzcproject01be.domains.reservation.repository;

import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.reservation.entity.QReservation;
import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import com.mzcteam01.mzcproject01be.domains.room.entity.QRoom;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QReservationRepository {

    private final JPAQueryFactory queryFactory;

    /* 각 사용자의 예약 목록 조회 - 조회시점에서 가까운 일정순으로
    @Query(
            SELECT r
            FROM Reservation r
            JOIN FETCH r.room room
            JOIN FETCH room.organization
            WHERE r.user.id = :userId
                AND r.startAt >= :now
                AND r.deletedAt IS NULL
            ORDER BY r.startAt ASC )
    */
    public List<Reservation> findMyReservations(int userId, LocalDateTime now){

        QReservation reservation = QReservation.reservation;
        QRoom room = QRoom.room;
        QOrganization organization = QOrganization.organization;

        return queryFactory
                .selectFrom(reservation)
                .join(reservation.room, room).fetchJoin()
                .join(room.organization, organization).fetchJoin()
                .where(
                        reservation.user.id.eq(userId),
                        reservation.startAt.goe(now),
                        reservation.deletedAt.isNull()
                )
                .orderBy(reservation.startAt.asc())
                .fetch();

    }

    /*  지난 예약 조회 (오래된 순서대로)
    @Query(
            SELECT r
            FROM Reservation r
            JOIN FETCH r.user
            JOIN FETCH r.room room
            JOIN FETCH room.organization
            WHERE r.user.id = :userId
                AND r.startAt < :now
                AND r.deletedAt IS NULL
            ORDER BY r.startAt DESC )
    */
    public List<Reservation> findPastReservations(int userId, LocalDateTime now) {
        QReservation reservation = QReservation.reservation;
        QUser user = QUser.user;
        QRoom room = QRoom.room;
        QOrganization organization = QOrganization.organization;

        return queryFactory
                .selectFrom(reservation)
                .join(reservation.user, user).fetchJoin()
                .join(reservation.room, room).fetchJoin()
                .join(room.organization, organization).fetchJoin()
                .where(
                        reservation.user.id.eq(userId),
                        reservation.startAt.lt(now),
                        reservation.deletedAt.isNull()
                )
                .orderBy(reservation.startAt.desc())
                .fetch();
    }
}
