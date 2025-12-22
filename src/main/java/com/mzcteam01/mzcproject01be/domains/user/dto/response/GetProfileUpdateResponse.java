package com.mzcteam01.mzcproject01be.domains.user.dto.response;


import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProfileUpdateResponse {

    private String name;
    private String phone;
    private String address;
    private String addressDetail;
    private Integer type;


    private Integer fileId;
    private String fileUrl;

}