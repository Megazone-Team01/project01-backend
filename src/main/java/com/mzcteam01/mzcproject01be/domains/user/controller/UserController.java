package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

}
