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
    private final QReservation reservation = QReservation.reservation;

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

    /**
     * 특정 회의실의 특정 시간대에 예약이 있는지 확인 (중복 예약 방지)
     */
    public boolean existsByRoomIdAndTimeRange(int roomId, LocalDateTime startAt, LocalDateTime endAt) {
        Long count = queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(
                        reservation.room.id.eq(roomId),
                        reservation.deletedAt.isNull(),
                        // 시간 겹침 체크
                        reservation.startAt.lt(endAt),
                        reservation.endAt.gt(startAt)
                )
                .fetchOne();

        return count != null && count > 0;
    }
}
