package com.mzcteam01.mzcproject01be.domains.organization.dto.response;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetOrganizationResponse {
    private int id;
    private String name;
    private String addressCode;
    private String addressDetail;
    private String tel;
    private int ownerId;
    private String ownerName;
    private int status;

    public static AdminGetOrganizationResponse of(Organization organization){
        return AdminGetOrganizationResponse.builder()
                .id( organization.getId() )
                .name( organization.getName() )
                .addressCode(organization.getAddressCode())
                .addressDetail(organization.getAddressDetail())
                .tel(organization.getTel())
                .ownerId( organization.getOwner().getId() )
                .ownerName(organization.getOwner().getName())
                .status(organization.getStatus())
                .build();
    }
}
