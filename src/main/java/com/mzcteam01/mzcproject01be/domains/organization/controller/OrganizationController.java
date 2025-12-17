package com.mzcteam01.mzcproject01be.domains.organization.controller;

import com.mzcteam01.mzcproject01be.domains.organization.dto.request.CreateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.UpdateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;
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
            @ModelAttribute GetOrganizationRequest request
    ){
        return ResponseEntity.ok( service.list( request ) );
    }

    @GetMapping("/{id}")
    @Operation( summary = "해당하는 ID의 Organization에 대한 상세 정보 조회", description = "상세 정보 조회")
    public ResponseEntity<AdminGetOrganizationResponse> findById(
            @PathVariable int id
    ){
        return ResponseEntity.ok( service.findById( id ) );
    }


    @GetMapping("/{organizationId}/teachers")
    @Operation( summary = "해당 아카데미에 소속된 강사 목록 조회" )
    public ResponseEntity<List<GetOrganizationTeacherResponse>> findAllTeachersInOrganization(
            @PathVariable int organizationId
    ){
        //return ResponseEntity.ok( service.findOrganizationTeacher( organizationId ) );
        return null;
    }

    @GetMapping( "/{organizationId}/lectures")
    @Operation( summary = "해당 아카데미에 소속된 강의 목록 조회" )
    public ResponseEntity<List<GetOrganizationLectureResponse>> findAllLecturesInOrganization(
            @PathVariable int organizationId
    ){
        //return ResponseEntity.ok( service.findOrganizationLecture( organizationId ) );
        return null;
    }

    @GetMapping("/status/{status}")
    @Operation( summary = "특정 상태의 기관들 목록을 조회" )
    public ResponseEntity<List<GetOrganizationResponse>> findAllOrganizationStatus(
            @PathVariable int status
    ){
        GetOrganizationRequest request = new GetOrganizationRequest();
        request.setStatusCode( status );
        return ResponseEntity.ok( service.list( request ) );
    }

    @PatchMapping("/{id}")
    @Operation( summary = "특정 기관 정보 업데이트" )
    public ResponseEntity<Void> updateOrganization(
            @PathVariable Integer id,
            @ModelAttribute UpdateOrganizationRequest request
    ){
        service.update( id, request );
        return ResponseEntity.ok().body( null );
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "특정 기관 삭제" )
    public ResponseEntity<Void> deleteOrganization(
            @PathVariable Integer id,
            @RequestParam int deletedBy
    ){
        service.delete( id, deletedBy );
        return ResponseEntity.ok().body( null );
    }

    @PostMapping("/create")
    @Operation( summary = "기관 생성" )
    public ResponseEntity<Void> createOrganization(
            @RequestBody CreateOrganizationRequest request
    ){
        service.create( request );
        return ResponseEntity.ok().body( null );
    }

    @PostMapping( "/{id}/apporve" )
    @Operation( summary = "기관 승인" )
    public ResponseEntity<Void> approveOrganization(
            @PathVariable Integer id
    ){
        service.approve( id );
        return ResponseEntity.ok().body( null );
    }
    @PostMapping( "/{id}/reject" )
    @Operation( summary = "기관 반려" )
    public ResponseEntity<Void> rejectOrganization(
            @PathVariable Integer id
    ){
        service.reject( id );
        return ResponseEntity.ok().body( null );
    }
}
