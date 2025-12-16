package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOnlineLectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineLectureRepository extends JpaRepository<OnlineLecture, Integer> {
}
