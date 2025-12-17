package com.mzcteam01.mzcproject01be.domains.user.dto.request;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class GetUserRequest {
    private String searchString;
    private UserRole userRole;
    private Integer type;
}
