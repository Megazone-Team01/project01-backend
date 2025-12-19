package com.mzcteam01.mzcproject01be.domains.meeting.controller;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.MyMeetingListResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.service.MeetingService;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherDetailResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.TeacherListResponse;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherListResponse>> getTeachersList(@RequestParam int organizationId) {

        List<TeacherListResponse> teachers = meetingService.getTeachersList(organizationId);

        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/teachers/{teacherId}")
    public ResponseEntity<TeacherDetailResponse> getTeacherDetails(@PathVariable int teacherId) {

        TeacherDetailResponse teacherDetail = meetingService.getTeacherDetails(teacherId);
        return ResponseEntity.ok(teacherDetail);
    }

    @GetMapping("/my")
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
