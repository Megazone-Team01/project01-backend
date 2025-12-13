package com.mzcteam01.mzcproject01be.domains.lecture.service.facade.interfaces;

public interface LectureService {
    OfflineLectureService offline();
    OnlineLectureService online();
}
