package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetUserDetailResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String addressDetail;
    private String roleName;
    private Integer type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String profileImage;
    private List<String> lectures;
    private List<String> organizations;

    public static AdminGetUserDetailResponse of(User user, List<Lecture> lectures, List<UserOrganization> organizations) {
        List<String> organization = organizations.stream().map( org -> org.getOrganization().getName()).toList();
        List<String> lecture = lectures.stream().map(Lecture::getName).toList();
        return AdminGetUserDetailResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .phone( user.getPhone() )
                .address( user.getAddress() )
                .addressDetail( user.getAddressDetail() )
                .roleName( user.getRole().getName() )
                .type( user.getType() )
                .createdAt( user.getCreatedAt() )
                .updatedAt( user.getUpdatedAt() )
                .deletedAt( user.getDeletedAt() )
                .profileImage( user.getProfileImg() )
                .lectures( lecture )
                .organizations( organization )
                .build();
    }
}
