package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;

public interface OfflineLectureService extends LectureService<
        OfflineLecture,
        LectureOfflineDetailResponse,
        LectureOfflineListResponse> {
        // 오프라인 전용 메서드 추가 가능
        // void updateLocation(int id, String newLocation);
}