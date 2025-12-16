package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.LectureRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
* SearchType = 1 (최신순)
* SearchType = 2 (날짜순)
* SearchType = 3 (인기순) // 개발 예정
* */

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer>, LectureRepositoryCustom {
}
