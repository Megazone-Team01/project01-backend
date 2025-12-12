package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
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
@RequestMapping("/api/v1/lecture/online")
public class OnlineLectureController {

    private final LectureService lectureService;


    @GetMapping
    public ResponseEntity<List<GetLectureResponse>> homeOnline(){
        log.info("Controller.Get.HomeOnline");
        List<GetLectureResponse> online = lectureService.getAllOnlineLectures(null);
        return ResponseEntity.ok(online);
    }

    @GetMapping("/courses")
    public ResponseEntity<LectureOnlineListResponse> onlineList(
            @RequestParam(required = false) Integer searchTypeCode,
            @RequestParam(defaultValue = "1") int page
    ) {

        try{
            SearchType searchType = searchTypeCode != null ?
                    SearchType.fromCode(searchTypeCode)
                    : SearchType.Lately;

            log.info("검색 조건: {} ({})", searchType.getCategorys(), searchType.getCode());
            LectureOnlineListResponse response = lectureService.getOnlineLecture(searchTypeCode, page);

            return ResponseEntity.ok().body(response);
        } catch (CustomException e) {
            log.error("Controller.onlile.courses.error: {}",e.getMessage());
          return ResponseEntity.badRequest().build();
        }
    }

  @GetMapping("/{onlineId}")
  public ResponseEntity<LectureOnlineDetailResponse> online(
            @PathVariable int onlineId
  ) {
      try {
          LectureOnlineDetailResponse online = lectureService.findOnlineLecture(onlineId);
          log.info("Controller.Online, onlineId: {} data : {}", onlineId,online);
          return ResponseEntity.ok().body(online);
      } catch (CustomException e){
          log.error("Controller.Online.error, onlineId: {}, error: {}", onlineId,e.getMessage());
          return ResponseEntity.badRequest().build();
      }
  }
}
