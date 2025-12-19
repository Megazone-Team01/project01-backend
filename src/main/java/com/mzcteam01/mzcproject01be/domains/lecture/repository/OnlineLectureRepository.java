package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineLectureRepository extends JpaRepository<OnlineLecture, Integer> {
    List<OnlineLecture> findAllByStatus( int status );
    List<Lecture> findAllByTeacherId(int teacherId );
}
