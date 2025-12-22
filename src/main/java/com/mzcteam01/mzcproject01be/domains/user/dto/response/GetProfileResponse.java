package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProfileResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String addressDetail;
    private String roleName;
    private String type;

    // 널처리
    private static String nvl(String value) {
        return value == null ? "" : value;
    }

    public static GetProfileResponse of(User user ){
        return GetProfileResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .phone( nvl(user.getPhone()) )
                .address(nvl(user.getAddress()))
                .addressDetail(nvl(user.getAddressDetail()))
                .roleName( user.getRole().getName() )
                .type( user.getType() == 0 ? "온/오프라인" : user.getType() == 1 ? "온라인" : "오프라인" )
                .build();
    }
}
