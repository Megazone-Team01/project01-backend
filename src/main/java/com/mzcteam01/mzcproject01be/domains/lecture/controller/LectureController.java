package com.mzcteam01.mzcproject01be.domains.lecture.controller;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.AdminCreateLectureRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
@Tag( name = "Lecture Controller", description = "통합 강의 관련 컨트롤러 ")
public class LectureController {
    private final LectureFacade lectureFacade;

    @GetMapping("/filter")
    @Operation( summary = "강의 조회" )
    public ResponseEntity<List<AdminGetLectureResponse>> getAllLectures(
            @RequestParam(required = false) Integer isOnline,
            @RequestParam(required = false) Integer status
    ) {
        return ResponseEntity.ok( lectureFacade.getAllLecturesWithFilter( isOnline, status ) );
    }

    @GetMapping("/{id}/{isOnline}")
    @Operation( summary = "강의 상세 조회" )
    public ResponseEntity<AdminGetLectureDetailResponse> getLectureById(
            @PathVariable int id,
            @PathVariable int isOnline
    ){
        return ResponseEntity.ok( lectureFacade.getLectureDetail( id, isOnline == 1) );
    }

    @PostMapping()
    @Operation( summary = "강의 생성" )
    public ResponseEntity<Void> createLecture(
        @RequestBody AdminCreateLectureRequest request
    ){
        lectureFacade.create( request );
        return ResponseEntity.ok( null );
    }

    @PostMapping("/approve/{id}")
    @Operation( summary = "강의 승인" )
    public ResponseEntity<Void> approveLecture(
            @PathVariable Integer id
    ){
        lectureFacade.approve( id );
        return ResponseEntity.ok( null );
    }

    @PostMapping("/reject/{id}")
    @Operation( summary = "강의 반려" )
    public ResponseEntity<Void> rejectLecture(
            @PathVariable Integer id
    ){
        lectureFacade.reject( id );
        return ResponseEntity.ok( null );
    }

}
