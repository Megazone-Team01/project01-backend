package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.HomeLectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final HomeLectureRepository homeLectureRepository;

    public List<GetLectureResponse> getAllOfflineLectures() {

        PageRequest pageOffline = PageRequest.of(0, 9);

        return homeLectureRepository.findAllOfflineLecture(pageOffline)
              .stream()
              .map(GetLectureResponse::of)
              .toList();

    }

    public List<GetLectureResponse> getAllOnlineLectures() {

        PageRequest pageOnline = PageRequest.of(0, 9);

        return homeLectureRepository.findAllOnlineLecture(pageOnline)
                .stream()
                .map(GetLectureResponse::of)
                .toList();
    }


}
