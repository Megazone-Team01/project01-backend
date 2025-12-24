package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfflineMeetingRepository extends JpaRepository<OfflineMeeting, Integer> {
    List<OfflineMeeting> findAllByStudentId(int studentId );
    List<OfflineMeeting> findAllByTeacherId( int teacherId );
    List<OfflineMeeting> findAllByOrganizationId( int organizationId );
}
