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
public class GetOrganizationResponse {
    private int id;
    private String name;
    private String addressCode;
    private String addressDetail;
    private String tel;
    private String ownerName;
    private String status;

    public static GetOrganizationResponse of(Organization organization){
        return GetOrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .addressCode(organization.getAddressCode())
                .tel(organization.getTel())
                .ownerName(organization.getOwner().getName())
                .status( organization.getStatus() == -1 ? "거절" : organization.getStatus() == 0 ? "요청" : "운영중" )
                .build();
    }
}
