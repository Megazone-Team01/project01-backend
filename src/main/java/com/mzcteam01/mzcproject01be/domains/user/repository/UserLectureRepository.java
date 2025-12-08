package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLectureRepository extends JpaRepository<UserLecture, Integer> {
}
