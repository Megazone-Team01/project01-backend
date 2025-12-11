package com.mzcteam01.mzcproject01be.domains.lecture.repository;


import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineRepository extends JpaRepository<OfflineLecture, Integer> {
}
