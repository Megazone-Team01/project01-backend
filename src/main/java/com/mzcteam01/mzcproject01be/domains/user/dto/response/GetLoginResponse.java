package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetLoginResponse {
    private Integer id;
    private String email;
    private String name;
    private String role;
    private int type;

    private String accessToken;
    private String refreshToken;
}
