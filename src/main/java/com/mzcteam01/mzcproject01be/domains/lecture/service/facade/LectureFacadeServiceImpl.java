package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

import com.mzcteam01.mzcproject01be.domains.lecture.service.facade.interfaces.LectureService;
import com.mzcteam01.mzcproject01be.domains.lecture.service.facade.interfaces.OfflineLectureService;
import com.mzcteam01.mzcproject01be.domains.lecture.service.facade.interfaces.OnlineLectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacadeServiceImpl implements LectureService {

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
