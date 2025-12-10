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
        log.info("Controller.Get.HomeOffline");
        List<GetLectureResponse> offline = homeService.getAllOfflineLectures();

        return ResponseEntity.ok(offline);
    }

    @GetMapping("/online")
    public ResponseEntity<List<GetLectureResponse>> homeOnline(){
        log.info("Controller.Get.HomeOnline");
        List<GetLectureResponse> online = homeService.getAllOnlineLectures();
        return ResponseEntity.ok(online);
    }



}
