package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse {
    private Integer id;
    private String email;
    private String name;
}
