package com.mzcteam01.mzcproject01be.domains.room.repository;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByOrganizationId( int organizationId );

    // organization_id와 deletedAt으로 필터링
    List<Room> findAllByOrganizationIdAndDeletedAtIsNull(Integer organizationId);

    // deletedAt이 NULL인 것만
    List<Room> findAllByDeletedAtIsNull();
}
