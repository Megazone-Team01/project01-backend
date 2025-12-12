package com.mzcteam01.mzcproject01be.domains.lecture.service.facade;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;

import java.util.List;

// 공통 인터페이스
public interface LectureService<T extends Lecture, D, L> {
    D findLecture(int id);
    List<GetLectureResponse> getTop9Lectures(Integer searchType);
    L getAllLectures(Integer searchType, int page);
}

