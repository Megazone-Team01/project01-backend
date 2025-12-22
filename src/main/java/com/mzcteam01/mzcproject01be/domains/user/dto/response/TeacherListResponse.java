package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeacherListResponse {

    private int teacherId;
    private String name;
    private int type;
    private String typeName;
    private String profileImage;
    private boolean available; // 상담 가능 여부
    // private List<String> categories; // 담당 강의 카테고리 목록

    public static TeacherListResponse from(User teacher) {
        ChannelType channelType = ChannelType.fromCode(teacher.getType());

        return TeacherListResponse.builder()
                .teacherId(teacher.getId())
                .name(teacher.getName())
                .type(teacher.getType())
                .typeName(ChannelType.ALL.getDescription())
                .profileImage(teacher.getProfileImg())
                .available(true) // 기본값으로 현재는 모두 상담 가능, 추후 가용 시간 체크 로직에 추가 후 변경
                //.categories(categoryNames)
                .build();
    }
}
