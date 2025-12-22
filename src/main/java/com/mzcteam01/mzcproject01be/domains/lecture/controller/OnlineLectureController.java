package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.OfflineLectureUploadRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.OnlineLectureUploadRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.lecture.enums.SearchType;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.LectureService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserLectureService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecture/online")
public class OnlineLectureController {

    private final LectureService lectureService;
    private final UserLectureService userLectureService;


    @GetMapping
    public ResponseEntity<List<GetLectureResponse>> homeOnline(){
        log.info("Controller.Get.HomeOnline");
        List<GetLectureResponse> online = lectureService
                .online()
                .getTop9Lectures(null);
        return ResponseEntity.ok(online);
    }

    @GetMapping("/courses")
    public ResponseEntity<LectureOnlineListResponse> onlineList(
            @RequestParam(required = false) Integer searchTypeCode,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String keyword
    ) {

        try{
            SearchType searchType = searchTypeCode != null ?
                    SearchType.fromCode(searchTypeCode)
                    : SearchType.LATELY;
            log.info("Controller.Online.onlineList keyword!!: {}",keyword);
            log.info("검색 조건: {} ({})", searchType.getCategorys(), searchType.getCode());
            LectureOnlineListResponse response = lectureService
                    .online()
                    .getAllLectures(searchType.getCode(), page, keyword);

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
          LectureOnlineDetailResponse online = lectureService
                  .online()
                  .findLecture(onlineId);
          log.info("Controller.Online, onlineId: {} data : {}", onlineId,online);
          return ResponseEntity.ok().body(online);
      } catch (CustomException e){
          log.error("Controller.Online.error, onlineId: {}, error: {}", onlineId,e.getMessage());
          return ResponseEntity.badRequest().build();
      }
  }

  @PostMapping("{onlineId}")
    public ResponseEntity<UserEnrolledResponse> apply(
          @PathVariable int onlineId,
          @AuthenticationPrincipal UserDetails userDetails,
          Authentication authentication
          ){

      log.info("🔍 받은 lectureId: {}" , onlineId);
      log.info("🔍 받은 user: {}", userDetails.getUsername());

      AuthUser authUser = (AuthUser) authentication.getPrincipal();
      int userId = authUser.getId();

      userLectureService.create(userId, onlineId, true, LocalDateTime.now());
      return ResponseEntity.status(HttpStatus.CREATED).body(UserEnrolledResponse.of(true));
  }

    @DeleteMapping("{onlineId}")
    public ResponseEntity<?> delete(
            @PathVariable int onlineId,
            Authentication authentication
    ){
        log.info("✅ onlineId: {}, online.lecture.delete" , onlineId);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        int userId = authUser.getId();

        userLectureService.delete(userId, onlineId, true);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadOffline(
            @Valid @ModelAttribute OnlineLectureUploadRequest request,
            @AuthenticationPrincipal UserDetails userDetails,
            Authentication authentication
    ){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        int userId = authUser.getId();
        log.info("orgainzaition {}",request.getOrganizationId());
        log.info("Controller.Offline.upload {}, authUser: {},offline: {}", request, authUser.getId(),request.getIsOnline());
        lectureService.online().createOfflineLecture(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
