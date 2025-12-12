package com.mzcteam01.mzcproject01be.domains.user.dto.response;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGetUserLectureResponse {
    private int id;
    private int userId;
    private String userName;
    private int lectureId;
    private boolean isOnline;
    private String lectureName;
    private LocalDateTime registeredAt;
    private LocalDateTime expiredAt;

    public static AdminGetUserLectureResponse of(UserLecture userLecture, String lectureName ){
        return AdminGetUserLectureResponse.builder()
                .id( userLecture.getId() )
                .userId( userLecture.getUser().getId() )
                .userName( userLecture.getUser().getName() )
                .lectureId( userLecture.getLectureId() )
                .isOnline( userLecture.getIsOnline() == 1 )
                .lectureName( lectureName )
                .registeredAt( userLecture.getRegisteredAt() )
                .expiredAt( userLecture.getExpiredAt() )
                .build();
    }
}
