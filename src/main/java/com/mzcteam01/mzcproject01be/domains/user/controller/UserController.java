package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.GetUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.user.service.UserOrganizationService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.UpdateStatusUserOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetApproveOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetProfileResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserOrganizationService userOrganizationService;

    @PostMapping("/signup")
    public ResponseEntity<GetUserResponse> signup(@RequestBody CreateUserRequest createUserRequest) {
        GetUserResponse user = userService.signup(createUserRequest);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<GetLoginResponse> login(@RequestBody LoginRequest loginRequest) {
        GetLoginResponse user = userService.login(loginRequest);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping()
    @Operation( summary = "모든 사용자 조회 API" )
    public ResponseEntity<List<AdminGetUserResponse>> getUser(
            @ModelAttribute GetUserRequest request
    ) {
        return ResponseEntity.ok( userService.list( request ) );
    }

    @GetMapping("/{id}")
    @Operation( summary = "특정 사용자 상세 조회 API" )
    public ResponseEntity<AdminGetUserDetailResponse> getUserDetailById(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok( userService.getUserDetailById(id) );
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "사용자 삭제 API" )
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id,
            @RequestParam Integer deletedBy
    ){
        userService.delete( id, deletedBy );
        return ResponseEntity.ok().body( null );
    }

    @GetMapping("/teacher/organization")
    @Operation( summary = "모든 기관의 선생님 조회" )
    public ResponseEntity<List<AdminGetUserOrganizationResponse>> getOrganizationTeacher(){
        return ResponseEntity.ok( userOrganizationService.findAllTeacher() );
    }

    @GetMapping("/teacher/organization/{id}")
    @Operation( summary = "특정 기관의 선생님 조회" )
    public ResponseEntity<List<AdminGetUserOrganizationResponse>> getOrganizationTeacherById(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok( userOrganizationService.findAllByOrganizationId( id ) );
    }
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfileInfo(@AuthenticationPrincipal AuthUser authUser) {
        int id = authUser.getId();
        GetProfileResponse my = userService.getProfileInfo(id);
        return ResponseEntity.ok().body(my);
    }

//    @PutMapping("/my")
//    public ResponseEntity<GetMyResponse> putMyInfo(@AuthenticationPrincipal AuthUser authUser) {
//        int id = authUser.getId();
//        GetMyResponse my = userService.putMyInfo(id);
//        return ResponseEntity.ok().body(my);
//    }
//
//    @DeleteMapping("/my")
//    public ResponseEntity<Map<String,String>> deleteMyInfo(@AuthenticationPrincipal AuthUser authUser) {
//        int id = authUser.getId();
//        userService.deleteMyInfo(id);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","회원 탈퇴되었습니다."));
//    }

    // 기관 승인 요청 화면의 조회
    @GetMapping("/approveOrganization")
    public ResponseEntity<List<GetApproveOrganizationResponse>> getApproveOrganization(@AuthenticationPrincipal AuthUser authUser) {
        int id = authUser.getId();
        List<GetApproveOrganizationResponse> users = userService.approveOrganization(id);
        return ResponseEntity.ok(users);
    }

    // 기관 승인 요청 화면의 승인, 거절 처리
    @PatchMapping("/approveOrganization")
    public ResponseEntity<Map<String,String>> updateStatus(@RequestBody UpdateStatusUserOrganizationRequest request) {
        userService.updateStatusUserOrganization(request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","처리 완료하였습니다."));
    }
}
