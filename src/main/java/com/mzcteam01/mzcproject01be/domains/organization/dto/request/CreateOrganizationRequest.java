package com.mzcteam01.mzcproject01be.domains.organization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CreateOrganizationRequest
{
    private String name;
    private String webpage;
    private Integer ownerId;
    private String tel;
    private String addressCode;
    private String addressDetail;
    private Integer isOnline;
    private String description;
}
