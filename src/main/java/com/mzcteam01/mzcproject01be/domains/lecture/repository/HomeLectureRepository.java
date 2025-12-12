package com.mzcteam01.mzcproject01be.domains.lecture.repository;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeLectureRepository extends JpaRepository<OfflineLecture, Integer> {

    @Query("select of from OfflineLecture of order by of.id desc")
    List<OfflineLecture> findAllOfflineLecture(Pageable pageable);

    @Query("select ol from OnlineLecture ol order by ol.id desc")
    List<OnlineLecture> findAllOnlineLecture(Pageable pageable);


}
