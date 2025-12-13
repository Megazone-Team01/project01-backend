package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.LectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.facade.interfaces.OfflineLectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OfflineLectureServiceImpl implements OfflineLectureService {

    private final LectureRepository lectureRepository;
    private final OfflineLectureRepository offlineLectureRepository;

    @Override
    public LectureOfflineDetailResponse findLecture(int id) {
        OfflineLecture offlineLecture = offlineLectureRepository.findById(id).
                orElseThrow(() -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage()));
        return LectureOfflineDetailResponse.of(offlineLecture);
    }


    @Override
    public List<GetLectureResponse> getTop9Lectures(Integer searchType) {

        PageRequest pageRequest = PageRequest.of(0, 9);
        return  lectureRepository.findOfflineLectures(searchType, pageRequest)
                .stream()
                .map(GetLectureResponse::of)
                .toList();
    }

    @Override
    public LectureOfflineListResponse getAllLectures(Integer searchType, int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<OfflineLecture> offline = lectureRepository.findOfflineLectures(searchType, pageRequest);
        return LectureOfflineListResponse.of(offline);
    }



}
