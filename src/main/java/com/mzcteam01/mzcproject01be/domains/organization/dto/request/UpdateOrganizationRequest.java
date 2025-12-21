package com.mzcteam01.mzcproject01be.domains.organization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrganizationRequest {
    private String homepage;
    private String tel;
    private String addressCode;
    private String addressDetail;
    private Integer type;
    private String description;
}
