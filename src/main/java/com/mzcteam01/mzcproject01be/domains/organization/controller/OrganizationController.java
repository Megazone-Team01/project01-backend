package com.mzcteam01.mzcproject01be.domains.organization.controller;

import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationLectureResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationTeacherResponse;
import com.mzcteam01.mzcproject01be.domains.organization.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/v1/organization" )
@Tag( name = "Organization Controller", description = "아카데미(기관)에 대한 API" )
public class OrganizationController {
    private final OrganizationService service;

    @GetMapping()
    @Operation( summary = "조건에 따른 Organization 목록 조회", description = "현재는 status에 따른 구분만 가능 ( 추후 필터 검색 기능 추가시 구현 예정 )")
    public ResponseEntity<List<GetOrganizationResponse>> list(
            @RequestBody GetOrganizationRequest request
    ){
        return ResponseEntity.ok( service.list( request ) );
    }

    @GetMapping("/{id}")
    @Operation( summary = "해당하는 ID의 Organization에 대한 상세 정보 조회", description = "상세 정보 조회")
    public ResponseEntity<GetOrganizationResponse> findById(
            @PathVariable int id
    ){
        return ResponseEntity.ok( service.findById( id ) );
    }


    @GetMapping("/{organizationId}/teachers")
    @Operation( summary = "해당 아카데미에 소속된 강사 목록 조회" )
    public ResponseEntity<List<GetOrganizationTeacherResponse>> findAllTeachersInOrganization(
            @PathVariable int organizationId
    ){
        return ResponseEntity.ok( service.findOrganizationTeacher( organizationId ) );
    }

    @GetMapping( "/{organizationId}/lectures")
    @Operation( summary = "해당 아카데미에 소속된 강의 목록 조회" )
    public ResponseEntity<List<GetOrganizationLectureResponse>> findAllLecturesInOrganization(
            @PathVariable int organizationId
    ){
        return ResponseEntity.ok( service.findOrganizationLecture( organizationId ) );
    }
}
