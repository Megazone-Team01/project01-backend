package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.enums.SearchType;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.LectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final OnlineRepository onlineRepository;
    private final OfflineRepository offlineRepository;

    public List<GetLectureResponse> getAllOfflineLectures(Integer searchType) {

        PageRequest pageOffline = PageRequest.of(0, 9);

        return lectureRepository.findAllOfflineLecture(searchType, pageOffline)
                .stream()
                .map(GetLectureResponse::of)
                .toList();

    }

    public List<GetLectureResponse> getAllOnlineLectures(Integer searchType) {

        PageRequest pageOnline = PageRequest.of(0, 9);

        return lectureRepository.findAllOnlineLecture(searchType, pageOnline)
                .stream()
                .map(GetLectureResponse::of)
                .toList();
    }

    public LectureOnlineListResponse getOnlineLecture(
            Integer searchType,
            int page
    ) {

        Pageable pageable = PageRequest.of(page, 20);

        Page<OnlineLecture> lectures = lectureRepository.findAllOnlineLecture(searchType, pageable);
        return LectureOnlineListResponse.of(lectures);
    }

    public LectureOfflineListResponse getOfflineLecture(
            Integer searchType,
            int page
    ) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<OfflineLecture> lectures = lectureRepository.findAllOfflineLecture(searchType, pageable);
        return LectureOfflineListResponse.of(lectures);
    }

    public LectureOnlineDetailResponse findOnlineLecture(int onlineId){
        OnlineLecture online = onlineRepository.findById(onlineId)
                .orElseThrow(()-> new CustomException("존재하지 않는 강의입니다!"));
        return LectureOnlineDetailResponse.of(online);
    }

    public LectureOfflineDetailResponse findOfflineLecture(int offlineId){
       OfflineLecture offline = offlineRepository.findById(offlineId)
               .orElseThrow(() -> new CustomException("존재하지 않는 강의입니다!"));

       return LectureOfflineDetailResponse.of(offline);
    }

}



