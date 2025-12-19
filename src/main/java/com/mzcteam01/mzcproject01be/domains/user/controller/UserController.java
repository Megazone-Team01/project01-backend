package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetProfileResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
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

}
