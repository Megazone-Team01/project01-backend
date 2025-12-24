package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.*;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.user.service.UserOrganizationService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetApproveOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetProfileResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
@Tag( name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;
    private final UserOrganizationService userOrganizationService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "신규 사용자를 가입시키고 사용자 정보를 반환합니다.")
    public ResponseEntity<GetUserResponse> signup(@RequestBody CreateUserRequest createUserRequest) {
        GetUserResponse user = userService.signup(createUserRequest);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인 후 토큰 및 사용자 정보를 반환합니다.")
    public ResponseEntity<GetLoginResponse> login(@RequestBody LoginRequest loginRequest) {
        GetLoginResponse user = userService.login(loginRequest);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping()
    @Operation( summary = "모든 사용자 조회" )
    public ResponseEntity<List<AdminGetUserResponse>> getUser(
            @ModelAttribute GetUserRequest request
    ) {
        return ResponseEntity.ok( userService.list( request ) );
    }

    @GetMapping("/{id}")
    @Operation( summary = "특정 사용자 상세 조회" )
    public ResponseEntity<AdminGetUserDetailResponse> getUserDetailById(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok( userService.getUserDetailById(id) );
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "사용자 삭제" )
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal AuthUser authUser
    ){
        userService.delete( id, authUser.getId() );
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
        return ResponseEntity.ok( userOrganizationService.findAllTeacherByOrganizationId( id ) );
    }

    @GetMapping("/profile")
    @Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필 정보를 조회합니다.")
    public ResponseEntity<GetProfileResponse> getProfileInfo(@AuthenticationPrincipal AuthUser authUser) {
        int id = authUser.getId();

        GetProfileResponse my = userService.getProfileInfo(id);

        return ResponseEntity.ok().body(my);
    }

    @PutMapping("/profile")
    @Operation(summary = "내 프로필 수정", description = "로그인한 사용자가 자신의 프로필 정보를 수정합니다.")
    public ResponseEntity<GetProfileUpdateResponse> putMyInfo(@AuthenticationPrincipal AuthUser authUser,
                                                        @RequestBody UpdateUserRequest request) {
        int id = authUser.getId();
        GetProfileUpdateResponse my = userService.updateProfileInfo(id, request);
        return ResponseEntity.ok().body(my);
    }

    @DeleteMapping("/profile")
    @Operation(summary = "회원 탈퇴", description = "로그인한 사용자가 자신의 계정을 삭제합니다.")
    public ResponseEntity<Map<String,String>> deleteMyInfo(@AuthenticationPrincipal AuthUser authUser) {
        int id = authUser.getId();
        userService.deleteUserInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","회원 탈퇴되었습니다."));
    }

    // 기관 승인 요청 화면의 조회
    @GetMapping("/approveOrganization")
    @Operation(summary = "기관 승인 요청 조회", description = "로그인한 사용자의 기관 승인 요청 목록을 조회합니다.")
    public ResponseEntity<List<GetApproveOrganizationResponse>> getApproveOrganization(@AuthenticationPrincipal AuthUser authUser) {
        int id = authUser.getId();
        List<GetApproveOrganizationResponse> users = userService.approveOrganization(id);
        return ResponseEntity.ok(users);
    }

    // 기관 승인 요청 화면의 승인, 거절 처리
    @PatchMapping("/approveOrganization")
    @Operation(summary = "기관 승인 요청 처리", description = "기관 승인 요청에 대해 승인/거절 처리를 합니다.")
    public ResponseEntity<Map<String,String>> updateStatus(@RequestBody UpdateStatusUserOrganizationRequest request) {
        userService.updateStatusUserOrganization(request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","처리 완료하였습니다."));
    }

    @PatchMapping("/update/{id}/admin")
    @Operation( summary = "관리자의 사용자 업데이트" )
    public ResponseEntity<GetProfileUpdateResponse> putMyInfo(
            @RequestBody UpdateUserRequest request,
            @PathVariable Integer id
    ) {
        GetProfileUpdateResponse my = userService.updateProfileInfo(id, request);
        return ResponseEntity.ok().body(my);
    }
}
