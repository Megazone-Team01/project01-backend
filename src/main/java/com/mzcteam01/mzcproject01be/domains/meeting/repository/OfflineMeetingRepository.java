package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineMeetingRepository extends JpaRepository<OfflineMeeting, Integer> {
}
