package com.mzcteam01.mzcproject01be.domains.organization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationRequest {
    private Integer ownerId;
    private Integer statusCode;
    private String name;
    private Integer isOnline;
    private String searchString;
}
