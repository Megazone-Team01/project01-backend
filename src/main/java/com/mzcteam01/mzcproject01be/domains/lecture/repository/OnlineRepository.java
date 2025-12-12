package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineRepository extends JpaRepository<OnlineLecture, Integer> {
}
