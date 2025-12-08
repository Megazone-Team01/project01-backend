package com.mzcteam01.mzcproject01be.domains.room.repository;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
