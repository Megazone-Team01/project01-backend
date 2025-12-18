package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetApproveOrganizationResponse {
    private Integer id;
    private String email;
    private String name;
    private String phone;

    public static GetApproveOrganizationResponse of(User user) {
        return GetApproveOrganizationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }
}
