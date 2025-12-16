package com.mzcteam01.mzcproject01be.domains.user.dto.request;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRequest {
    private String searchString;
    private UserRole userRole;
    private Integer type;
}
