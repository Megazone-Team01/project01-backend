package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLectureRepository extends JpaRepository<UserLecture, Integer> {
    List<UserLecture> findAllByUserId(int userId);
    List<UserLecture> findAllByLectureIdAndIsOnline(int lectureId, int isOnline);
    boolean existsByUserIdAndLectureId(int userId, int lectureId);
    UserLecture findByUserIdAndLectureId(int userId, int lectureId);

    @Query(value = "SELECT lecture_id FROM user_lecture WHERE user_id = :userId", nativeQuery = true)
    List<Integer> findLectureIdsByUserId(@Param("userId") int userId);
}
