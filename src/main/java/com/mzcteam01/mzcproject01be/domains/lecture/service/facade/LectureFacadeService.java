package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

public interface LectureFacadeService {
    OfflineLectureService offline();
    OnlineLectureService online();
}
