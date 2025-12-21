package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.OfflineLectureService;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OfflineLectureServiceImplTest {

    @Autowired
    private OfflineLectureService offlineLectureService;
    @Autowired
    private UserLectureRepository userLectureRepository;

    @Test
    void applyLecture() {
        int userId = 1;
        int lectureId = 101;

        boolean exists = userLectureRepository.existsByUserIdAndLectureId(userId, lectureId);
        assertTrue(exists);
    }
}