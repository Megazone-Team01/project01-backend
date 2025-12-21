package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetUserResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String addressDetail;
    private String roleName;
    private String type;
    private boolean isDeleted;
    private LocalDateTime createdAt;

    public static AdminGetUserResponse of( User user ){
        return AdminGetUserResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .phone( user.getPhone() )
                .address( user.getAddress() )
                .addressDetail( user.getAddressDetail() )
                .roleName( user.getRole().getName() )
                .type( user.getType() == 0 ? "온/오프라인" : user.getType() == 1 ? "온라인" : "오프라인" )
                .isDeleted( user.getDeletedAt() != null )
                .createdAt( user.getCreatedAt() )
                .build();
    }
}
