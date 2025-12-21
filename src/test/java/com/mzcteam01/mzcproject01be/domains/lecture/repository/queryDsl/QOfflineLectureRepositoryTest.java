package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.UserStatusResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QOfflineLectureRepositoryTest {
    @Autowired
    private QOfflineLectureRepository offlineLectureRepository;

    @Test
    void status() {
        UserStatusResponse status = offlineLectureRepository.status(1);

    }
}