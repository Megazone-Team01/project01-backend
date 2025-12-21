package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLectureRepository extends JpaRepository<UserLecture, Integer> {
    List<UserLecture> findAllByUserId(int userId);
    boolean existsByUserIdAndLectureId(int userId, int offlineId);
}
