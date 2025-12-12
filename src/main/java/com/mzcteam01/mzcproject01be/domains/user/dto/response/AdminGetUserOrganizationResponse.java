package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetUserOrganizationResponse {
    private int id;
    private int userId;
    private String userName;
    private int organizationId;
    private String organizationName;
    private LocalDateTime registeredAt;
    private LocalDateTime expiredAt;

    public static AdminGetUserOrganizationResponse of(UserOrganization userOrganization){
        return AdminGetUserOrganizationResponse.builder()
                .id( userOrganization.getId() )
                .userId( userOrganization.getUser().getId() )
                .userName( userOrganization.getUser().getName() )
                .organizationId( userOrganization.getOrganization().getId() )
                .organizationName( userOrganization.getOrganization().getName() )
                .registeredAt( userOrganization.getRegisteredAt() )
                .expiredAt( userOrganization.getExpiredAt() )
                .build();
    }
}
