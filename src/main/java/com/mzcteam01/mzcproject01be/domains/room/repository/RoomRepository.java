package com.mzcteam01.mzcproject01be.domains.room.repository;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    // 특정 기관의 특정 상태인 회의실 목록 조회
    // TODO : JPQL 쿼리 작성
    List<Room> findByOrganizationIdAndStatus(Integer organizationId, RoomStatus status);

}
