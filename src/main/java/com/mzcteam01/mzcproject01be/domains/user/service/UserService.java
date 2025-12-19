package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetProfileResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    // 회원가입
    public GetUserResponse signup(CreateUserRequest request);

    // 로그인
    public GetLoginResponse login(LoginRequest request);

    // 마이페이지 조회
    public GetProfileResponse getProfileInfo(int id);

//    // 마이페이지 수정
//    public GetMyResponse putMyInfo(int id);
//
//    // 마이페이지 탈퇴
//    public void deleteMyInfo(int id);

}
