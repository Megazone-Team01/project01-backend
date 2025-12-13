package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.LectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.LectureRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOfflineLecture.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class LectureServiceImplTest {
    @Autowired
    private LectureRepository lectureRepository;

    @Test
    public void lectureRepositoryTest(){
        PageRequest request = PageRequest.of(1, 2);
        Page<OfflineLecture> lectures = lectureRepository.findOfflineLectures(1, request);

        System.out.println(lectures.getTotalPages());
        System.out.println(lectures.getTotalElements());
    }
}
