package com.mzcteam01.mzcproject01be.domains.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    // 로그인 시 필요한 이메일과 비밀번호
    private String email;
    private String password;
}
