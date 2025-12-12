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
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    // ===== Base CRUD Method ===== //
    public void create(int organizationId, int userId, LocalDateTime checkIn, LocalDateTime checkOut) {
        User user = userRepository.findById(userId).orElseThrow( () -> new CustomException("해당하는 사용자가 없습니다") );
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        Attendance attendance = Attendance.builder()
                .user( user )
                .organization( organization )
                .checkIn( LocalDateTime.now() )
                .checkOut( null )
                .build();
        attendanceRepository.save( attendance );
    }
    public void update( int attendanceId, LocalDateTime checkIn, LocalDateTime checkOut) {
        Attendance attendance = attendanceRepository.findById( attendanceId ).orElseThrow(
                () -> new CustomException("해당 출석이 존재하지 않습니다")
        );
        attendance.update( checkIn, checkOut );
    }

    public List<AdminGetAttendanceResponse> findAll() {
        return attendanceRepository.findAll().stream().map(AdminGetAttendanceResponse::of ).toList();
    }

    public AdminGetAttendanceDetailResponse findById( int attendanceId) {
        return AdminGetAttendanceDetailResponse.of( attendanceRepository.findById( attendanceId ).orElseThrow(
                () -> new CustomException("해당하는 출석이 존재하지 않습니다")
        ));
    }

    public void delete( int attendanceId, int deletedBy ) {
        Attendance attendance = attendanceRepository.findById( attendanceId ).orElseThrow(
                () -> new CustomException("해당하는 출석이 존재하지 않습니다")
        );
        attendance.delete( deletedBy );
    }
}
