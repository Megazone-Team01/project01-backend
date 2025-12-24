package com.mzcteam01.mzcproject01be.domains.organization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationWithRoomsResponse {
    private Integer id;
    private String name;
    private Integer isOnline;
    private List<RoomInfo> rooms;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfo {
        private Integer id;
        private String name;
        private Integer maxNum;
        private String location;
    }
}
