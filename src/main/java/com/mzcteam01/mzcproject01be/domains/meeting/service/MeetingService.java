package com.mzcteam01.mzcproject01be.domains.meeting.service;

import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetOfflineMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetOnlineMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherDetailResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherListResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MeetingService {
    void create(boolean isOnline, String name, int organizationId, int teacherId, int studentId, LocalDateTime startAt, LocalDateTime endAt,
                String location, Integer roomId );
    void updateOnlineMeeting( int id, String name, LocalDateTime startAt, LocalDateTime endAt, String location );
    void updateOfflineMeeting( int id, String name, LocalDateTime startAt, LocalDateTime endAt, Integer roomId );
    void delete( boolean isOnline, int id, int deletedBy );
    List<AdminGetMeetingResponse> findAll();
    AdminGetOnlineMeetingResponse findOnlineMeetingById(int id );
    AdminGetOfflineMeetingResponse findOfflineMeetingById(int id );

    List<TeacherListResponse> getTeachersList(int organizationId);

    TeacherDetailResponse getTeacherDetails(int teacherId);

    // List<MyMeetingListResponse> getMyMeetings(int studentId, Integer status);
}
