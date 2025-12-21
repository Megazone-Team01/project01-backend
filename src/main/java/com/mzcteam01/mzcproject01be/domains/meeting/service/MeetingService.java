package com.mzcteam01.mzcproject01be.domains.meeting.service;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.request.ApproveMeetingRequest;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.request.CreateMeetingRequest;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherDetailResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherListResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MeetingService {
    void create(boolean isOnline, String name, int organizationId, int teacherId, int studentId, LocalDateTime startAt, LocalDateTime endAt,
                String location, Integer roomId);

    void updateOnlineMeeting(int id, String name, LocalDateTime startAt, LocalDateTime endAt, String location);

    void updateOfflineMeeting(int id, String name, LocalDateTime startAt, LocalDateTime endAt, Integer roomId);

    void delete(boolean isOnline, int id, int deletedBy);

    List<AdminGetMeetingResponse> findAll();

    AdminGetOnlineMeetingResponse findOnlineMeetingById(int id);

    AdminGetOfflineMeetingResponse findOfflineMeetingById(int id);

    List<TeacherListResponse> getTeachersList(int organizationId);

    TeacherDetailResponse getTeacherDetails(int teacherId);

    CreateMeetingResponse createMeeting(int studentId, CreateMeetingRequest request);

    void cancelMeeting(int studentId, int meetingId, boolean isOnline);

    List<MyMeetingListResponse> getMyMeetings(int studentId, ChannelType channelType, Integer status);

    void approveMeeting(int teacherId, int meetingId, boolean isOnline, ApproveMeetingRequest request);

    void rejectMeeting(int teacherId, int meetingId, boolean isOnline, String reason);

}
