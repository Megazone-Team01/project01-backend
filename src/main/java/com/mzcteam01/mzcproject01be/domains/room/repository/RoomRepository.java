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
    List<Room> findAllByOrganizationId( int organizationId );

    // organization_id와 deletedAt으로 필터링
    List<Room> findAllByOrganizationIdAndDeletedAtIsNull(Integer organizationId);

    // deletedAt이 NULL인 것만
    List<Room> findAllByDeletedAtIsNull();
}
