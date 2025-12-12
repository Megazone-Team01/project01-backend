package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacadeServiceImpl implements LectureFacadeService {

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
