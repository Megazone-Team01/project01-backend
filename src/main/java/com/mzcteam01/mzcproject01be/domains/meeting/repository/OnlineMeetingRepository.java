package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineMeetingRepository extends JpaRepository<OnlineMeeting, Integer> {
    List<OnlineMeeting> findAllByStudentId( int studentId );
    List<OnlineMeeting> findAllByTeacherId( int teacherId );
    List<OnlineMeeting> findAllByOrganizationId( int organizationId );
}
