package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.UpdateStatusUserOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetApproveOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

//    @GetMapping("/my")
//    public ResponseEntity<UserDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
//        // userDetails 에서 현재 로그인한 사용자 ID 가져오기
//        Long userId = userDetails.getId();
//
//        UserDto user = userService.getUserById(userId);
//        return ResponseEntity.ok(user);
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
