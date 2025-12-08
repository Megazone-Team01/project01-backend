package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
