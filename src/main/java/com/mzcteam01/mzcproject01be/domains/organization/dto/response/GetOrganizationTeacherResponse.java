package com.mzcteam01.mzcproject01be.domains.organization.dto.response;

import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrganizationTeacherResponse {
    private int id;
    private String name;
    private String email;

    public static GetOrganizationTeacherResponse of( User user ){
        return GetOrganizationTeacherResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .build();
    }

}
