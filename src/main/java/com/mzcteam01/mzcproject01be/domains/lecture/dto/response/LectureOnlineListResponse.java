package com.mzcteam01.mzcproject01be.domains.lecture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LectureOnlineListResponse {
    private List<GetLectureResponse> onlineList;
}
