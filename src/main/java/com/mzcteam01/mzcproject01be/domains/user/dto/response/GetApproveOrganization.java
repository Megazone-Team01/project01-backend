package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetApproveOrganization {
    private Integer id;
    private String email;
    private String name;
    private String phone;
}
