package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusResponse {
    // 유저가 조직 승인 받았는지 status 반환
    private int status;
}
