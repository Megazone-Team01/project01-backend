package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface LectureRepositoryCustom {

    Page<OfflineLecture> findOfflineLectures(
            @Param("SearchType") Integer SearchType,
            Pageable pageable);

    Page<OnlineLecture> findOnlineLectures(
            @Param("SearchType") Integer SearchType,
            Pageable pageable);


}
