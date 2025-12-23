package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.OfflineLectureUploadRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.UserEnrolledResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.enums.SearchType;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureFacade;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.LectureService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserLectureService;
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
@RequestMapping("/api/v1/lecture/offline")
@RequiredArgsConstructor
public class OfflineLectureController {

    private final LectureService lectureService;
    private final UserLectureService userLectureService;

    @GetMapping
    public ResponseEntity<List<GetLectureResponse>> homeOffline() {
        log.info("Controller.Get.HomeOffline");
        List<GetLectureResponse> offline = lectureService
                .offline()
                .getTop9Lectures(null);
        return ResponseEntity.ok(offline);
    }

    @GetMapping("/courses")
    public ResponseEntity<LectureOfflineListResponse> offlineList(
            @RequestParam(required = false) Integer searchTypeCode,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String keyword

    ) {

        try {
            SearchType searchType = searchTypeCode != null ?
                    SearchType.fromCode(searchTypeCode)
                    : SearchType.LATELY;

            log.info("검색 조건: {} ({})", searchType.getCategorys(), searchType.getCode());
//            log.info("searchKeyword: {}", searchKeyword);
            LectureOfflineListResponse response = lectureService.offline().getAllLectures(searchType.getCode(), page,keyword);
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            log.error("error : {}",e.getMessage());
            throw  new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage());
        }

    }

    @GetMapping("/{offlineId}")
    public ResponseEntity<LectureOfflineDetailResponse> offline(
            @PathVariable int offlineId,
            Authentication authentication
    ){
        try{
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            int userId = authUser.getId();
            LectureOfflineDetailResponse offline = lectureService.offline().findLecture(offlineId);
            boolean exists = userLectureService.UserAppliedLecture(userId, offlineId);

            LectureOfflineDetailResponse response = offline.toBuilder()
                    .exists(exists)
                    .build();


            log.info("Controller.Offline.offline, offlineId: {} data : {}", offlineId,offline);
            return ResponseEntity.ok().body(response);
        } catch (CustomException e){
            log.error("Controller.Offline.error, offlineId: {}, error: {}", offlineId,e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{offlineId}")
    public ResponseEntity<UserEnrolledResponse> apply(
            @PathVariable int offlineId,
            @AuthenticationPrincipal UserDetails userDetails,
            Authentication authentication
       ){
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        int userId = authUser.getId();

        log.info("🔍 받은 lectureId: {}" , offlineId);
        log.info("🔍 받은 user: {}", userDetails.getUsername());


        userLectureService.create(userId, offlineId, false, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserEnrolledResponse.of(true));

    }

    @DeleteMapping("{offlineId}")
    public ResponseEntity<?> delete(
            @PathVariable int offlineId,
            @AuthenticationPrincipal UserDetails userDetails,
            Authentication authentication
    ){
        log.info("✅ offlineId: {}, offline.lecture.delete" , offlineId);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        int userId = authUser.getId();

        userLectureService.delete(userId, offlineId, false);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadOffline(
            @Valid @ModelAttribute OfflineLectureUploadRequest request,
            @AuthenticationPrincipal UserDetails userDetails,
            Authentication authentication
            ){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        int userId = authUser.getId();
        log.info("orgainzaition {}",request.getOrganizationId());
        log.info("Controller.Offline.upload {}, authUser: {},offline: {}", request, authUser.getId(),request.getIsOnline());
        lectureService.offline().createOfflineLecture(request,userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
