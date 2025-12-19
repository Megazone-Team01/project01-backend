package com.mzcteam01.mzcproject01be.domains.user.dto.request;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    // 회원가입 시 필요한 이메일과 비밀번호.
    private String email;
    private String password;
    private String passwordConfirm;

    // 최소한의 회원정보(역할, 이름, 전화번호, 주소[addressCode])
    private String role;
    private String name;
    private String phone;
    private String addressCode;
    private String type;
}
