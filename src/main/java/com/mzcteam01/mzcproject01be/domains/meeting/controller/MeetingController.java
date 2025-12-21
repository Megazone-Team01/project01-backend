package com.mzcteam01.mzcproject01be.domains.meeting.controller;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.request.ApproveMeetingRequest;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.request.CreateMeetingRequest;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.CreateMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.MyMeetingListResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.service.MeetingService;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherDetailResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherListResponse;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/meetings")
@RequiredArgsConstructor
@Tag(name = "Meeting", description = "상담 API")
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/teachers")
    @Operation( summary = "상담 가능한 선생님 목록 조회",
                description = "organizationId로 기관별 상담 가능한 선생님 목록을 조회함")
    public ResponseEntity<List<TeacherListResponse>> getTeachersList(@RequestParam int organizationId) {

        List<TeacherListResponse> teachers = meetingService.getTeachersList(organizationId);

        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/teachers/{teacherId}")
    @Operation( summary = "선생님 상세 정보 조회",
                description = "선생님 상세 정보 조회함")
    public ResponseEntity<TeacherDetailResponse> getTeacherDetails(@PathVariable int teacherId) {

        TeacherDetailResponse teacherDetail = meetingService.getTeacherDetails(teacherId);
        return ResponseEntity.ok(teacherDetail);
    }

    @PostMapping
    @Operation(summary = "상담 예약 생성", description = "학생이 선생님과의 상담을 예약함")
    public ResponseEntity<CreateMeetingResponse> createMeeting(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody CreateMeetingRequest request
    ) {
        int studentId = authUser.getId();
        CreateMeetingResponse response = meetingService.createMeeting(studentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my")
    @Operation( summary = "내 상담 목록 조회",
                description = "로그인한 학생의 상담 목록을 조회함")
    public ResponseEntity<List<MyMeetingListResponse>> getMyMeetings(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(defaultValue = "ALL") String type,
            @RequestParam(required = false) Integer status
    ) {
        int studentId = authUser.getId();
        ChannelType channelType = ChannelType.fromName(type);
        List<MyMeetingListResponse> meetings = meetingService.getMyMeetings(studentId, channelType, status);
        return ResponseEntity.ok(meetings);
    }

    @DeleteMapping("/{meetingId}")
    @Operation(summary = "상담 취소", description = "학생이 본인의 상담 예약을 취소함")
    public ResponseEntity<Map<String, String>> cancelMeeting(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable int meetingId,
            @RequestParam boolean isOnline
    ) {
        int studentId = authUser.getId();
        meetingService.cancelMeeting(studentId, meetingId, isOnline);
        return ResponseEntity.ok(Map.of("message", "상담이 취소되었습니다."));
    }

    @PatchMapping("/{meetingId}/approve")
    @Operation(summary = "상담 승인", description = "선생님이 상담을 승인")
    public ResponseEntity<Map<String, String>> approveMeeting(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable int meetingId,
            @RequestParam boolean isOnline,
            @RequestBody ApproveMeetingRequest request
    ) {
        int teacherId = authUser.getId();
        meetingService.approveMeeting(teacherId, meetingId, isOnline, request);
        return ResponseEntity.ok(Map.of("message", "상담이 승인되었습니다."));
    }


}
