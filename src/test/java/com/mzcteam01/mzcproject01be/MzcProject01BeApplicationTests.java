package com.mzcteam01.mzcproject01be;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.service.HomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MzcProject01BeApplicationTests {

    @Autowired
    HomeService homeService;

    @Test
    @Transactional(readOnly = true)
    void contextLoads() {

    }

}
