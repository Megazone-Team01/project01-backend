package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOfflineLectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class QOfflineLectureRespositoryTest {
    @Autowired
    QOfflineLectureRepository qOfflineLectureRepository;

    @Test
    public void offlineRepositoryTest() {


    }
}
