package com.mzcteam01.mzcproject01be.domains.lecture.service.basic;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.*;

import java.util.List;
// 처음 썼던 방식
public interface LectureService {
    // 상세 조회
    LectureOnlineDetailResponse findOnlineLecture(int onlineId);
    LectureOfflineDetailResponse findOfflineLecture(int offlineId);

    // 신규 강좌 (9개)
    List<GetLectureResponse> getAllOfflineLectures(Integer searchType);
    List<GetLectureResponse> getAllOnlineLectures(Integer searchType);

    // 페이징 조회 (20개)
    LectureOnlineListResponse getOnlineLectures(Integer searchType, int page);
    LectureOfflineListResponse getOfflineLectures(Integer searchType, int page);
}
