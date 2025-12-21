package com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;

public interface OfflineLectureService extends LectureFacadeService<
        OfflineLecture,
        LectureOfflineDetailResponse,
        LectureOfflineListResponse> {


}