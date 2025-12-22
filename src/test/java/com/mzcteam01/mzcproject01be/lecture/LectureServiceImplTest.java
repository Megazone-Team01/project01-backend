package com.mzcteam01.mzcproject01be.lecture;

import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureServiceImpl;
import com.mzcteam01.mzcproject01be.domains.lecture.service.OfflineLectureServiceImpl;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SpringBootTest
@Transactional(readOnly = true)
public class LectureServiceImplTest {
    @Autowired
    private QOfflineLectureRepository qOfflineLectureRepository;

    @Autowired
    private LectureServiceImpl lectureServiceImpl;
    @Autowired
    private OfflineLectureRepository offlineLectureRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    public void QofflineLecture() {
        Optional<Organization> byId = organizationRepository.findById(2);


    }

}
