package com.mzcteam01.mzcproject01be.domains.user.controller;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.GetUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.user.service.UserOrganizationService;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
