package com.mzcteam01.mzcproject01be.domains.organization.dto.response;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetOrganizationDetailResponse {
    private int id;
    private String name;
    private String address;
    private String addressDetail;
    private String tel;
    private Integer ownerId;
    private String ownerName;
    private String description;
    private Integer isOnline;
    private String webpage;
    private String leadImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<String> lectures;
    private List<String> students;
    private List<String> teachers;

    public static AdminGetOrganizationDetailResponse of(
            Organization organization,
            List<String> lectures,
            List<String> students,
            List<String> teachers
    ) {
        return AdminGetOrganizationDetailResponse.builder()
                .name(organization.getName())
                .id(organization.getId())
                .address(organization.getAddress())
                .addressDetail(organization.getAddressDetail())
                .tel(organization.getTel())
                .ownerId(organization.getOwner().getId())
                .ownerName(organization.getOwner().getName())
                .description(organization.getDescription())
                .isOnline(organization.getIsOnline())
                .webpage(organization.getWebpage())
                .leadImage(organization.getLeadImage())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .deletedAt(organization.getDeletedAt())
                .lectures( lectures )
                .students( students )
                .teachers( teachers )
                .build();
    }
}
