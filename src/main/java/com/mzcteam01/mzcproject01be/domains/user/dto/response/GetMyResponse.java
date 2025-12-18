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
public class GetMyResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String addressCode;
    private String addressDetail;
    private String roleName;
    private String type;

    public static GetMyResponse of( User user ){
        return GetMyResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .phone( user.getPhone() )
                .addressCode( user.getAddressCode() )
                .addressDetail( user.getAddressDetail() )
                .roleName( user.getRole().getName() )
                .type( user.getType() == 0 ? "온/오프라인" : user.getType() == 1 ? "온라인" : "오프라인" )
                .build();
    }
}
