package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.service.HomeService;
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
}
