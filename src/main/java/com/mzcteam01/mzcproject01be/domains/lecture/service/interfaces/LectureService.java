package com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces;

public interface LectureService {
    OfflineLectureService offline();
    OnlineLectureService online();
}
