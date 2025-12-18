package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.GetUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserDetailResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    // 회원가입
    public GetUserResponse signup(CreateUserRequest request);

    // 로그인
    public GetLoginResponse login(LoginRequest request);

    // 사용자 조회
    List<AdminGetUserResponse> list(GetUserRequest request );

    // 사용자 삭제
    void delete( int id, int deletedBy );
    AdminGetUserDetailResponse getUserDetailById( int id );

}
