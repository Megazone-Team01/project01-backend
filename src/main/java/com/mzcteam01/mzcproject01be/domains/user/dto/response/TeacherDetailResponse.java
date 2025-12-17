package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeacherDetailResponse {

    private int teacherId;
    private String name;
    private String email;
    private int type;
    private String typeName;
    //TODO : Lecture쪽에서 조회 메서드 추가 후 적용
    //private List<String> lectureNames;

    public static TeacherDetailResponse from(User teacher) {
        ChannelType channelType = ChannelType.fromCode(teacher.getType());

        return TeacherDetailResponse.builder()
                .teacherId(teacher.getId())
                .name(teacher.getName())
                .email(teacher.getEmail())
                .type(teacher.getType())
                .typeName(channelType.getDescription())
                //.lectureNames(lectureNames)
                .build();
    }
}
