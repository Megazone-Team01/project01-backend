package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOnlineLectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineLectureRepository extends JpaRepository<OfflineLecture, Integer> {
}
