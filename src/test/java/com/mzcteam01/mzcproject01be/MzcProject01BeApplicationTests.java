package com.mzcteam01.mzcproject01be;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
class MzcProject01BeApplicationTests {


    @Test
    @Transactional(readOnly = true)
    void contextLoads() {

    }

}
