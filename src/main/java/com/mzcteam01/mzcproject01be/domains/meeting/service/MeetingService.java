package com.mzcteam01.mzcproject01be.domains.meeting.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetOfflineMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetOnlineMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.Meeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OfflineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OnlineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
}
