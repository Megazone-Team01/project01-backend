package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String fileUrl;
    private List<String> lectures;
    private List<String> organizations;

    // 널처리
    private static String nvl(String value) {
        return value == null ? "" : value;
    }

    public static GetProfileResponse of(User user, List<Lecture> lectures, List<UserOrganization> organizations) {
        List<String> organization = organizations.stream().map( org -> org.getOrganization().getName()).toList();
        List<String> lecture = lectures.stream().map(Lecture::getName).toList();

        return GetProfileResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .phone( nvl(user.getPhone()) )
                .address( nvl(user.getAddress()) )
                .addressDetail( nvl(user.getAddressDetail()) )
                .roleName( user.getRole().getName() )
                .type( user.getType() == 0 ? "ALL" : user.getType() == 1 ? "ONLINE" : "OFFLINE" )
                .fileUrl( nvl(user.getFile().getUrl()) )
                .lectures( lecture )
                .organizations( organization )
                .build();
    }


}
