package com.mzcteam01.mzcproject01be.domains.room.repository;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    // 특정 기관의 특정 상태인 회의실 목록 조회
    @Query( """
            SELECT r
            FROM Room r
            JOIN FETCH r.organization
            WHERE r.organization.id =:organizationId
                AND r.status =:status
                AND r.deletedAt IS NULL
            ORDER BY r.name ASC
            """)
    List<Room> findByOrganizationIdAndStatus(Integer organizationId, RoomStatus status);


    @Query( """
            SELECT r
            FROM Room r
            JOIN FETCH r.manager
            JOIN FETCH r.organization
            WHERE r.id =:roomId
                AND r.deletedAt IS NULL
            """)
    Optional<Room> findByIdWithDetails(Integer roomId);

}
