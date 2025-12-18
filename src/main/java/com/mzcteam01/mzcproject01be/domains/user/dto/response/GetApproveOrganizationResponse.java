package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
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
    private int userid;
    private String email;
    private String name;
    private String phone;

    // UserOrganization과 User 둘 다 받도록
    public static GetApproveOrganizationResponse of(UserOrganization userOrganization) {
        User user = userOrganization.getUser();
        return GetApproveOrganizationResponse.builder()
                .id(userOrganization.getId())  // 여기 추가
                .userid(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }
}
