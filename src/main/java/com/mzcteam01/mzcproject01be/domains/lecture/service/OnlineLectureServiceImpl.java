package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOnlineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOnlineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.OnlineLectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OnlineLectureServiceImpl implements OnlineLectureService {

    private final OnlineLectureRepository onlineLectureRepository;
    private final QOnlineLectureRepository qOnlineLectureRepository;

    @Override
    public LectureOnlineDetailResponse findLecture(int id) {
        OnlineLecture onlineLecture = onlineLectureRepository.findById(id)
                .orElseThrow(() -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage()));
        return LectureOnlineDetailResponse.of(onlineLecture);
    }

    @Override
    public List<GetLectureResponse> getTop9Lectures(Integer searchType) {
        PageRequest pageRequest = PageRequest.of(0, 9);
        return qOnlineLectureRepository.findOnlineLectures(searchType, pageRequest)
                .stream()
                .map(GetLectureResponse::of)
                .toList();

    }

    @Override
    public LectureOnlineListResponse getAllLectures(Integer searchType, int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<OnlineLecture> onlineLecture = qOnlineLectureRepository.findOnlineLectures(searchType, pageRequest);
        return LectureOnlineListResponse.of(onlineLecture);
    }

}
