package com.mzcteam01.mzcproject01be.domains.meeting.controller;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
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


}
