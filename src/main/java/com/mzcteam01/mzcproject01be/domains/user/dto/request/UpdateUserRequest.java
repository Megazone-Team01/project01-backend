package com.mzcteam01.mzcproject01be.domains.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    // 회원 수정 시 필요한 값
    private String name;
    private String phone;
    private String address;
    private String addressDetail;
    private String type;


    private Integer fileId;


}

