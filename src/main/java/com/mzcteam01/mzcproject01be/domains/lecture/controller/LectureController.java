package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private final HomeService homeService;

    @GetMapping("/offline")
    public ResponseEntity<List<GetLectureResponse>> homeOffline() {
        log.info("home Offline");
        List<GetLectureResponse> offlineList = homeService.getAllLectures()
                .stream()
                .map(GetLectureResponse::of)
                .toList();

        return ResponseEntity.ok(offlineList);
    }

    @GetMapping("/online")
    public ResponseEntity<List<GetLectureResponse>> homeOnline(){
        log.info("home Online");

        List<GetLectureResponse> onlineLectures = homeService.getAllOnlineLectures()
                .stream()
                .map(GetLectureResponse::of)
                .toList();


        return ResponseEntity.ok(onlineLectures);
    }



}
