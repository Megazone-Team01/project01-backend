package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEnrolledResponse {
    private boolean isEnrolled;

    public static UserEnrolledResponse of(boolean isEnrolled) {
        return UserEnrolledResponse.builder()
                .isEnrolled(isEnrolled)
                .build();
    }
}
