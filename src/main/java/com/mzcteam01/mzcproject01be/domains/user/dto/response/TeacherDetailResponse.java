package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TeacherDetailResponse {

    private int teacherId;
    private String name;
    private String email;
    private int type;
    private String typeName;
    private List<String> lectureNames;

    public static TeacherDetailResponse from(User teacher, List<String> lectureNames) {
        ChannelType channelType = ChannelType.fromCode(teacher.getType());

        return TeacherDetailResponse.builder()
                .teacherId(teacher.getId())
                .name(teacher.getName())
                .email(teacher.getEmail())
                .type(teacher.getType())
                .typeName(channelType.getDescription())
                .lectureNames(lectureNames)
                .build();
    }
}
