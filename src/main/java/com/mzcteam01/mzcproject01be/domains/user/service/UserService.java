package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetApproveOrganizationResponse;
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

    // 조직 가입 요청한 강사조회
    public List<GetApproveOrganizationResponse> approveOrganization(int id);

}
