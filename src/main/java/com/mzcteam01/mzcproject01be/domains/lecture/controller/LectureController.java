package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.LectureRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.lecture.enums.SearchType;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureService;
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

    private final LectureService lectureService;


    @GetMapping("/offline")
    public ResponseEntity<List<GetLectureResponse>> homeOffline() {
        log.info("Controller.Get.HomeOffline");
        log.info("LectureController.homeOffline ${hello}");
        List<GetLectureResponse> offline = lectureService.getAllOfflineLectures(null);

        return ResponseEntity.ok(offline);
    }

    @GetMapping("/online")
    public ResponseEntity<List<GetLectureResponse>> homeOnline(){
        log.info("Controller.Get.HomeOnline");
        List<GetLectureResponse> online = lectureService.getAllOnlineLectures(null);
        return ResponseEntity.ok(online);
    }

    @GetMapping("/online/courses")
    public ResponseEntity<LectureOnlineListResponse> onlineList(
            @RequestParam(required = false) Integer searchTypeCode,
            @RequestParam(defaultValue = "1") int page
    ) {

        SearchType searchType = null;
        // code가 있으면 code로 변환, 없으면 enum 이름 사용, 둘 다 없으면 기본값
        if (searchTypeCode != null) {
            searchType = SearchType.fromCode(searchTypeCode);
        } else if (searchType == null) {
            searchType = SearchType.Lately;
        }

        log.info("검색 조건: {} ({})", searchType.getCategorys(), searchType.getCode());
        LectureOnlineListResponse response = lectureService.getOnlineLecture(searchTypeCode, page);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/offline/courses")
    public ResponseEntity<LectureOfflineListResponse> offlineList(
            @RequestParam(required = false) Integer searchTypeCode,
            @RequestParam(defaultValue = "1") int page
    ) {

        try {
            SearchType searchType = searchTypeCode == null ?
                    SearchType.fromCode(searchTypeCode)
                    : SearchType.Lately;

            log.info("검색 조건: {} ({})", searchType.getCategorys(), searchType.getCode());
            LectureOfflineListResponse response = lectureService.getOfflineLecture(searchTypeCode, page);
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            throw  new CustomException("searchType null");
        }

    }


//  @GetMapping("/offline/{offlineId}")
//  public ResponseEntity<GetLectureResponse> offline(
//          @PathVariable int offlineId
//  ) {
//    log.info("Controller.Get.offline: {}", offlineId);
//    return null;
//  }

  @GetMapping("/online/{onlineId}")
  public ResponseEntity<LectureOnlineDetailResponse> online(
            @PathVariable int onlineId
  ) {
      LectureOnlineDetailResponse online = lectureService.findOnlineLecture(onlineId);
      log.info("Controller.Online.offline, onlineId: {} data : {}", onlineId,online);
      return ResponseEntity.ok().body(online);
  }


  @GetMapping("/offline/{offlineId}")
    public ResponseEntity<LectureOfflineDetailResponse> offline(
            @PathVariable int offlineId
  ){
        LectureOfflineDetailResponse offline = lectureService.findOfflineLecture(offlineId);
        log.info("Controller.Offline.offline, offlineId: {} data : {}", offlineId,offline);
        return ResponseEntity.ok().body(offline);
  }



}
