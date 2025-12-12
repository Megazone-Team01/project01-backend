package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
* SearchType = 1 (최신순)
* SearchType = 2 (날짜순)
* */

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    @Query("select ol from OfflineLecture ol " +
            "order by " +
            "CASE when coalesce(:SearchType, 1) = 1 then ol.createdAt end desc, "+
            "CASE WHEN coalesce(:SearchType, 1) = 2 THEN ol.createdAt END ASC")
    Page<OfflineLecture> findAllOfflineLecture(
            @Param("SearchType") Integer SearchType,
            Pageable pageable);

    @Query("select ol from OnlineLecture ol " +
            "order by " +
            "CASE when coalesce(:SearchType, 1) = 1 then ol.createdAt end desc, "+
            "CASE WHEN coalesce(:SearchType, 1) = 2 THEN ol.createdAt END ASC")
    Page<OnlineLecture> findAllOnlineLecture(
            @Param("SearchType") Integer SearchType,
            Pageable pageable);
}
