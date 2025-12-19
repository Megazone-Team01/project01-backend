package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfflineLectureRepository extends JpaRepository<OfflineLecture, Integer> {
    List<Lecture> findAllByTeacherId(int teacherId );
}
