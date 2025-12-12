package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetLoginResponse {
    private Integer id;
    private String email;
    private String name;
}
