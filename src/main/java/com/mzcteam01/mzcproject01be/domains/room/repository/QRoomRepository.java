package com.mzcteam01.mzcproject01be.domains.room.repository;

import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.room.entity.QRoom;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class QRoomRepository {

    private final JPAQueryFactory queryFactory;

    /* 특정 기관의 특정 상태인 회의실 목록 조회
    @Query( SELECT r
            FROM Room r
            JOIN FETCH r.organization
            WHERE r.organization.id =:organizationId
                AND r.status =:status
                AND r.deletedAt IS NULL
            ORDER BY r.name ASC )
    */
    public List<Room> findByOrganizationIdAndStatus(int organizationId, RoomStatus status) {
        QRoom room = QRoom.room;
        QOrganization organization = QOrganization.organization;

        return queryFactory
                .selectFrom(room)
                .join(room.organization, organization).fetchJoin()
                .where(
                        room.organization.id.eq(organizationId),
                        room.status.eq(status),
                        room.deletedAt.isNull()
                )
                .orderBy(room.name.asc())
                .fetch();
    }


    /*
    @Query( SELECT r
            FROM Room r
            JOIN FETCH r.manager
            JOIN FETCH r.organization
            WHERE r.id =:roomId
                AND r.deletedAt IS NULL )
    */
    public Optional<Room> findByIdWithDetails(int roomId) {
        QRoom room = QRoom.room;
        QUser manager = QUser.user;
        QOrganization organization = QOrganization.organization;

        Room result = queryFactory
                .selectFrom(room)
                .join(room.manager, manager).fetchJoin()
                .join(room.organization, organization).fetchJoin()
                .where(
                        room.id.eq(roomId),
                        room.deletedAt.isNull()
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
