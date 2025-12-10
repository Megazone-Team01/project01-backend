package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.service.HomeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class LectureServiceTest {
    @Autowired
    private HomeService homeService;

    @Test
    @Transactional
    public void getOffline(){

        List<OfflineLecture> list = homeService.getAllLectures();

        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
        assertThat(list.get(0)).isEqualTo("오프라인 강의 3");


    }
}
