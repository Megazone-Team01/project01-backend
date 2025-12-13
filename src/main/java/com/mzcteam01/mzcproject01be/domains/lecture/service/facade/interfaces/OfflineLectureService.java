package com.mzcteam01.mzcproject01be.domains.lecture.service.facade.interfaces;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;

public interface OfflineLectureService extends LectureFacadeService<
        OfflineLecture,
        LectureOfflineDetailResponse,
        LectureOfflineListResponse> {

}