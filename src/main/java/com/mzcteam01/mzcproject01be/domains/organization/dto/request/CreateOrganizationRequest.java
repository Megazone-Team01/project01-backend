package com.mzcteam01.mzcproject01be.domains.organization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest
{
    private String name;
    private String webpage;
    private int ownerId;
    private String tel;
    private String addressCode;
    private String addressDetail;
    private int isOnline;
    private String description;
}
