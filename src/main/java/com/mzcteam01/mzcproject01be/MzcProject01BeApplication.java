package com.mzcteam01.mzcproject01be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MzcProject01BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzcProject01BeApplication.class, args);
    }

}
