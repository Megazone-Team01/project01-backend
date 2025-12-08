package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
}
