package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.OnlineLectureUploadRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.lecture.enums.SearchType;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.LectureService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserLectureService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag( name = "Online Lecture", description = "온라인 강의에 대한 API")
public class OnlineLectureController {

    private final LectureService lectureService;
    private final UserLectureService userLectureService;


    @GetMapping
    @Operation( summary = "홈 화면에서 온라인 강의를 조회" )
    public ResponseEntity<List<GetLectureResponse>> homeOnline(){
        log.info("Controller.Get.HomeOnline");
        List<GetLectureResponse> online = lectureService
                .online()
                .getTop9Lectures(null);
        return ResponseEntity.ok(online);
    }

    @GetMapping("/courses")
    @Operation( summary = "온라인 강의 목록 조회")
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
  @Operation( summary = "특정 온라인 강의에 대한 상세 조회")
  public ResponseEntity<LectureOnlineDetailResponse> online(
            @PathVariable int onlineId,
            Authentication authentication
  ) {
      try {
          AuthUser authUser = (AuthUser) authentication.getPrincipal();
          int userId = authUser.getId();
          LectureOnlineDetailResponse online = lectureService
                  .online()
                  .findLecture(onlineId);

          boolean exists = userLectureService.UserAppliedOnlineLecture(userId, onlineId, 1);
          log.info("Controller.Online.online: {}",exists);
          LectureOnlineDetailResponse response = online.toBuilder().exists(exists).build();


          log.info("Controller.Online, onlineId: {} data : {}", onlineId,online);
          return ResponseEntity.ok().body( response);
      } catch (CustomException e){
          log.error("Controller.Online.error, onlineId: {}, error: {}", onlineId,e.getMessage());
          return ResponseEntity.badRequest().build();
      }
  }

  @PostMapping("{onlineId}")
  @Operation( summary = "온라인 강의에 강의 신청")
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
    @Operation( summary = "특정 온라인 강의를 삭제")
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
    @Operation( summary = "오프라인 강의 생성")
    public ResponseEntity<?> uploadOffline(
            @Valid @ModelAttribute OnlineLectureUploadRequest request,
            @AuthenticationPrincipal UserDetails userDetails,
            Authentication authentication
    ){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        int userId = authUser.getId();
        log.info("video = {}", request.getVideo());
        log.info("name = {}", request.getName());
        log.info("orgainzaition {}",request.getOrganizationId());
        log.info("Controller.Offline.upload {}, authUser: {},offline: {}", request, authUser.getId(),request.getIsOnline());
        lectureService.online().createOfflineLecture(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
