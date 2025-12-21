package com.mzcteam01.mzcproject01be.domains.meeting.service;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.common.exception.*;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.request.CreateMeetingRequest;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.Meeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OfflineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OnlineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.QOfflineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.QOnlineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherDetailResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherListResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.QTeacherRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingServiceImpl implements MeetingService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OnlineMeetingRepository onlineMeetingRepository;
    private final OfflineMeetingRepository offlineMeetingRepository;
    private final QOnlineMeetingRepository qOnlineMeetingRepository;
    private final QOfflineMeetingRepository qOfflineMeetingRepository;
    private final QTeacherRepository qTeacherRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public void create(boolean isOnline, String name, int organizationId, int teacherId, int studentId, LocalDateTime startAt, LocalDateTime endAt,
                       String location, Integer roomId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        User student = userRepository.findById(studentId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()));

        if (isOnline) {
            OnlineMeeting meeting = OnlineMeeting.builder()
                    .name(name)
                    .organization(organization)
                    .teacher(teacher)
                    .student(student)
                    .startAt(startAt)
                    .endAt(endAt)
                    .status(0)
                    .location(location)
                    .build();
            onlineMeetingRepository.save(meeting);
        } else {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()));
            OfflineMeeting meeting = OfflineMeeting.builder()
                    .name(name)
                    .organization(organization)
                    .teacher(teacher)
                    .student(student)
                    .startAt(startAt)
                    .endAt(endAt)
                    .status(0)
                    .room(room)
                    .build();
            offlineMeetingRepository.save(meeting);
        }
    }

    @Override
    @Transactional

    public void updateOnlineMeeting(int id, String name, LocalDateTime startAt, LocalDateTime endAt, String location) {
        OnlineMeeting meeting = onlineMeetingRepository.findById(id).orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()));
        meeting.update(name, startAt, endAt, location);
    }

    @Override
    @Transactional
    public void updateOfflineMeeting(int id, String name, LocalDateTime startAt, LocalDateTime endAt, Integer roomId) {
        OfflineMeeting meeting = offlineMeetingRepository.findById(id).orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()));
        Room room = null;
        if (roomId != null) {
            room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()));
        }
        meeting.update(name, startAt, endAt, room);
    }

    @Override
    @Transactional
    public void delete(boolean isOnline, int id, int deletedBy) {
        Meeting meeting;
        if (isOnline)
            meeting = onlineMeetingRepository.findById(id).orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()));
        else
            meeting = offlineMeetingRepository.findById(id).orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()));
        meeting.delete(deletedBy);
    }

    @Override
    public List<AdminGetMeetingResponse> findAll() {
        List<AdminGetMeetingResponse> result = new ArrayList<>();
        onlineMeetingRepository.findAll().forEach(meeting -> {
            result.add(AdminGetMeetingResponse.of(meeting, true, meeting.getLocation()));
        });
        offlineMeetingRepository.findAll().forEach(meeting -> {
            result.add(AdminGetMeetingResponse.of(meeting, false, meeting.getRoom().getName()));
        });
        return result;
    }
    @Override
    public AdminGetOnlineMeetingResponse findOnlineMeetingById(int id) {
        return AdminGetOnlineMeetingResponse.of(onlineMeetingRepository.findById(id).orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage())));
    }
    @Override
    public AdminGetOfflineMeetingResponse findOfflineMeetingById(int id) {
        return AdminGetOfflineMeetingResponse.of(offlineMeetingRepository.findById(id).orElseThrow(() -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage())));
    }

    @Override
    public List<TeacherListResponse> getTeachersList(int organizationId) {

        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()));

        List<User> teachers = qTeacherRepository.findTeachersByOrganizaitonId(organizationId);

        List<TeacherListResponse> responseList = new ArrayList<>();

        for (User teacher : teachers) {
            TeacherListResponse response = TeacherListResponse.from(teacher);
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public TeacherDetailResponse getTeacherDetails(int teacherId) {
        User teacher = qTeacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new CustomException(UserErrorCode.TEACHER_NOT_FOUND.LOGIN_FAILED.getMessage()));

        return TeacherDetailResponse.from(teacher);

    }

    @Override
    @Transactional
    public CreateMeetingResponse createMeeting(int studentId, CreateMeetingRequest request) {

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new CustomException(UserErrorCode.TEACHER_NOT_FOUND.getMessage()));
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()));

        if (isTimeSlotBooked(request.getTeacherId(), request.getStartAt(), request.getEndAt())) {
            throw new CustomException(MeetingErrorCode.TIME_SLOT_NOT_AVAILABLE.getMessage());
        }

        // 온/오프라인 구분
        if (request.isOnline()) {
            OnlineMeeting meeting = OnlineMeeting.builder()
                    .name(request.getName())
                    .organization(organization)
                    .teacher(teacher)
                    .student(student)
                    .startAt(request.getStartAt())
                    .endAt(request.getEndAt())
                    .status(0)  // 대기 상태
                    .location(null)
                    .build();
            meeting.setCreatedBy(studentId);
            onlineMeetingRepository.save(meeting);

            return CreateMeetingResponse.success(
                    meeting.getId(),
                    request.getName(),
                    teacher.getName(),
                    request.getStartAt(),
                    request.getEndAt(),
                    true
            );
        } else  {
            Room room = null;
            if (request.getRoomId() != null) {
                room = roomRepository.findById(request.getRoomId())
                        .orElseThrow(() -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()));
            }

            OfflineMeeting meeting = OfflineMeeting.builder()
                    .name(request.getName())
                    .organization(organization)
                    .teacher(teacher)
                    .student(student)
                    .startAt(request.getStartAt())
                    .endAt(request.getEndAt())
                    .status(0)  // 대기 상태
                    .room(room)
                    .build();
            meeting.setCreatedBy(studentId);
            offlineMeetingRepository.save(meeting);

            return CreateMeetingResponse.success(
                    meeting.getId(),
                    request.getName(),
                    teacher.getName(),
                    request.getStartAt(),
                    request.getEndAt(),
                    false
            );
        }
    }

    @Override
    public List<MyMeetingListResponse> getMyMeetings(int studentId, ChannelType channelType, Integer status) {

        userRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));

        List<MyMeetingListResponse> result = new ArrayList<>();

        switch (channelType) {
            // 전체 조회: 온라인 + 오프라인
            case ALL -> {
                List<OnlineMeeting> onlineMeetings = qOnlineMeetingRepository.findByStudentId(studentId, status);
                List<OfflineMeeting> offlineMeetings = qOfflineMeetingRepository.findByStudentId(studentId, status);

                onlineMeetings.forEach(meeting -> result.add(MyMeetingListResponse.fromOnline(meeting)));
                offlineMeetings.forEach(meeting -> result.add(MyMeetingListResponse.fromOffline(meeting)));

                result.sort((a, b) -> b.getStartAt().compareTo(a.getStartAt()));
            }
            // 온라인만 조회
            case ONLINE -> {
                List<OnlineMeeting> onlineMeetings = qOnlineMeetingRepository.findByStudentId(studentId, status);
                onlineMeetings.forEach(meeting -> result.add(MyMeetingListResponse.fromOnline(meeting)));
            }
            // 오프라인만 조회
            case OFFLINE -> {
                List<OfflineMeeting> offlineMeetings = qOfflineMeetingRepository.findByStudentId(studentId, status);
                offlineMeetings.forEach(meeting -> result.add(MyMeetingListResponse.fromOffline(meeting)));
            }
        }

        return result;
    }

//    @Override
//    public AvailableTimesResponse getAvailableTimes(int teacherId, LocalDate date) {
//        // 선생님 존재 여부 확인
//        qTeacherRepository.findByTeacherId(teacherId)
//                .orElseThrow(() -> new CustomException(UserErrorCode.TEACHER_NOT_FOUND.getMessage()));
//
//        // 해당 날짜에 이미 예약된 시간대 조회
//        Set<String> bookedTimes = getBookedTimesForDate(teacherId, date);
//
//        // 시간대별 상태 생성
//        List<AvailableTimesResponse.TimeSlot> timeSlots = DEFAULT_TIME_SLOTS.stream()
//                .map(time -> {
//                    boolean isBooked = bookedTimes.contains(time);
//                    return AvailableTimesResponse.TimeSlot.builder()
//                            .time(time)
//                            .available(!isBooked)
//                            .status(isBooked ? "BOOKED" : "AVAILABLE")
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        return AvailableTimesResponse.builder()
//                .teacherId(teacherId)
//                .date(date)
//                .timeSlots(timeSlots)
//                .build();
//    }


    // Private 헬퍼 메서드

    /**
     * 특정 날짜에 이미 예약된 시간대 목록 조회
     */
    private Set<String> getBookedTimesForDate(int teacherId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        Set<String> bookedTimes = new HashSet<>();

        // 온라인 미팅에서 예약된 시간 조회
        List<OnlineMeeting> onlineMeetings = qOnlineMeetingRepository
                .findByTeacherIdAndDateRange(teacherId, startOfDay, endOfDay);

        for (OnlineMeeting meeting : onlineMeetings) {
            if (meeting.getDeletedAt() == null && meeting.getStatus() >= 0) { // 삭제되지 않고, 반려되지 않은 것
                String timeStr = String.format("%02d:00", meeting.getStartAt().getHour());
                bookedTimes.add(timeStr);
            }
        }

        // 오프라인 미팅에서 예약된 시간 조회
        List<OfflineMeeting> offlineMeetings = qOfflineMeetingRepository
                .findByTeacherIdAndDateRange(teacherId, startOfDay, endOfDay);

        for (OfflineMeeting meeting : offlineMeetings) {
            if (meeting.getDeletedAt() == null && meeting.getStatus() >= 0) {
                String timeStr = String.format("%02d:00", meeting.getStartAt().getHour());
                bookedTimes.add(timeStr);
            }
        }

        return bookedTimes;
    }

    /**
     *  중복 예약 검증 메서드 - 특정 시간대가 이미 예약되었는지 확인
     */
    private boolean isTimeSlotBooked(int teacherId, LocalDateTime startAt, LocalDateTime endAt) {
        // 온라인 미팅 중복 체크
        boolean onlineConflict = qOnlineMeetingRepository.existsByTeacherIdAndTimeRange(teacherId, startAt, endAt);
        if (onlineConflict) return true;

        // 오프라인 미팅 중복 체크
        return qOfflineMeetingRepository.existsByTeacherIdAndTimeRange(teacherId, startAt, endAt);
    }

}
