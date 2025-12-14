package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.LectureService;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.OfflineLectureService;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.OnlineLectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final OfflineLectureService offlineLectureService;
    private final OnlineLectureService onlineLectureService;

    @Override
    public OfflineLectureService offline() {
        return offlineLectureService;
    }

    @Override
    public OnlineLectureService online() {
        return onlineLectureService;
    }
}
