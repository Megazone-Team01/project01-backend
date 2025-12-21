package com.mzcteam01.mzcproject01be.domains.organization.dto.response;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrganizationResponse {
    private int id;
    private String name;
    private String address;
    private String addressDetail;
    private String tel;
    private String ownerName;
    private String description;
    private Integer isOnline;
    private String imageUrl;
    private String url;
    private String category;        // 추후 카테고리 조회 작업 방향에 따라 수정
    private boolean isDeleted;
    private LocalDateTime createdAt;

    public static GetOrganizationResponse of(Organization organization){
        return GetOrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .tel(organization.getTel())
                .ownerName(organization.getOwner().getName())
                .description( organization.getDescription())
                .isOnline( organization.getIsOnline() )
                .imageUrl( organization.getLeadImage() )
                .url( organization.getWebpage() )
                .isDeleted(organization.getDeletedBy() != null)
                .createdAt(organization.getCreatedAt())
                .build();
    }
}
