package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOfflineLectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class QOfflineLectureRespositoryTest {
    @Autowired
    QOfflineLectureRepository qOfflineLectureRepository;

    @Test
    public void offlineRepositoryTest() {
        PageRequest pageRequest = PageRequest.of(0, 9);
        Page<OfflineLecture> lectures = qOfflineLectureRepository.findOfflineLectures(1, pageRequest, null);

        System.out.println(lectures);
    }
}
