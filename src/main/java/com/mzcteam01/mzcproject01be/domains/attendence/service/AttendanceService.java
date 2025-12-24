package com.mzcteam01.mzcproject01be.domains.attendence.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.attendence.dto.response.AdminGetAttendanceDetailResponse;
import com.mzcteam01.mzcproject01be.domains.attendence.dto.response.AdminGetAttendanceResponse;
import com.mzcteam01.mzcproject01be.domains.attendence.entity.Attendance;
import com.mzcteam01.mzcproject01be.domains.attendence.repository.AttendanceRepository;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface AttendanceService {
    void create(int organizationId, int userId, LocalDateTime checkIn, LocalDateTime checkOut);
    void update( int attendanceId, LocalDateTime checkIn, LocalDateTime checkOut);
    List<AdminGetAttendanceResponse> findAll();
    AdminGetAttendanceDetailResponse findById(int attendanceId);
    void delete( int attendanceId, int deletedBy );
}
