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
    private String description;
    private String isOnline;
    private String imageUrl;
    private String url;
    private String category;        // 추후 카테고리 조회 작업 방향에 따라 수정
    private boolean isDeleted;

    public static GetOrganizationResponse of(Organization organization){
        return GetOrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .addressCode(organization.getAddressCode())
                .tel(organization.getTel())
                .ownerName(organization.getOwner().getName())
                .description( organization.getDescription())
                .isOnline( organization.getIsOnline() == 0 ? "온라인/오프라인" : organization.getIsOnline() == 1 ? "온라인" : "오프라인" )
                .imageUrl( organization.getLeadImage() )
                .url( organization.getWebpage() )
                .isDeleted(organization.getDeletedBy() != null)
                .build();
    }
}
