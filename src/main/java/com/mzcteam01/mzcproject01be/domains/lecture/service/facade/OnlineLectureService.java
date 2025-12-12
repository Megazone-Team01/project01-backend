package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOnlineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOnlineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;

// 온라인 강의 서비스 인터페이스
public interface OnlineLectureService extends LectureService<
        OnlineLecture,
        LectureOnlineDetailResponse,
        LectureOnlineListResponse> {

}
