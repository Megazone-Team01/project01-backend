package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.UpdateStatusUserOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetApproveOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetProfileResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.*;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    void delete( int id, int deletedBy );
    AdminGetUserDetailResponse getUserDetailById( int id );

    // 마이페이지 조회
    public GetProfileResponse getProfileInfo(int id);

    // 마이페이지 수정
    public GetProfileUpdateResponse updateProfileInfo(int id, UpdateUserRequest request);

    // 회원 탈퇴
    public void deleteUserInfo(int id);



    // 조직 가입 요청한 강사조회
    public List<GetApproveOrganizationResponse> approveOrganization(int id);

    // 조직 가입 처리(승인, 거절)
    public void updateStatusUserOrganization(UpdateStatusUserOrganizationRequest request);

}
