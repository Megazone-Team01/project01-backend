package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineMeetingRepository extends JpaRepository<OnlineMeeting, Integer> {
}
