package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.service.HomeService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOfflineLecture.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class LectureServiceTest {
    @Autowired
    private HomeService homeService;

    @PersistenceContext
     EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @Transactional
    public void getOffline(){


        List<GetLectureResponse> list = homeService.getAllOnlineLectures();



        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(9);
    }

    @Test
    @Transactional
    public void getOnline(){
        List<GetLectureResponse> list = homeService.getAllOfflineLectures();
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(8);
    }

    @Test
    @Transactional
    public void getQueryDslTest(){
        List<OfflineLecture> result = queryFactory
                .selectFrom(offlineLecture)
                .fetch();

        for (OfflineLecture lecture : result) {
            System.out.println("title: "+lecture.getName());
            System.out.println("description"+lecture.getDescription());
            System.out.println("------------------");
        }

        assertThat(result).isNotNull();


    }
}
