package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetApproveOrganizationResponse {
    private int id;
    private String email;
    private String name;
    private String phone;
    private String role;
    private String organizationName;

    // UserOrganization과 User 둘 다 받도록
    public static GetApproveOrganizationResponse of(UserOrganization userOrganization) {
        User user = userOrganization.getUser();
        Organization organization = userOrganization.getOrganization();

        return GetApproveOrganizationResponse.builder()
                .id(userOrganization.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .role(user.getRole().getName())
                .organizationName(organization.getName())
                .build();
    }
}
