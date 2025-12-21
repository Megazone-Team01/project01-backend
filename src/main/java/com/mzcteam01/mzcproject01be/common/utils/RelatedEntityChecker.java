package com.mzcteam01.mzcproject01be.common.utils;

import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.OrganizationErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OfflineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OnlineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.reservation.repository.ReservationRepository;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelatedEntityChecker {
    private final UserRepository userRepository;
    private final OnlineLectureRepository onlineLectureRepository;
    private final OfflineLectureRepository offlineLectureRepository;
    private final OnlineMeetingRepository onlineMeetingRepository;
    private final OfflineMeetingRepository offlineMeetingRepository;
    private final OrganizationRepository organizationRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    private final UserOrganizationRepository userOrganizationRepository;
    private final UserLectureRepository userLectureRepository;

    // with User
    public boolean relatedUserCheck( int userId ){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage())
        );
        if( !userOrganizationRepository.findAllByUserId( userId ).isEmpty() ) return false;
        if( !userLectureRepository.findAllByUserId( userId ).isEmpty() ) return false;
        if( !reservationRepository.findAllByUserId( userId ).isEmpty() ) return false;

        // teacher
        if( user.getRole().getName().equals("TEACHER") ){
            if( !onlineMeetingRepository.findAllByTeacherId( userId ).isEmpty() ) return false;
            if( !onlineLectureRepository.findAllByTeacherId( userId ).isEmpty() ) return false;
            if( !offlineLectureRepository.findAllByTeacherId( userId ).isEmpty() ) return false;
            if( !offlineMeetingRepository.findAllByTeacherId( userId ).isEmpty() ) return false;
        }
        else if( user.getRole().getName().equals("STUDENT") ){
            if( !onlineMeetingRepository.findAllByStudentId( userId ).isEmpty() ) return false;
            if( !offlineMeetingRepository.findAllByStudentId( userId ).isEmpty() ) return false;
        }
        return true;
    }

    // with Organization
    public boolean relatedOrganizationCheck( int organizationId ){
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage())
        );
        if( !userOrganizationRepository.findAllByOrganizationId( organizationId ).isEmpty() ) return false;
        if( !onlineLectureRepository.findAllByOrganizationId( organizationId ).isEmpty() ) return false;
        if( !offlineLectureRepository.findAllByOrganizationId( organizationId ).isEmpty() ) return false;
        if( !roomRepository.findAllByOrganizationId( organizationId ) .isEmpty() ) return false;
        return true;
    }

    // with online lecture
    public boolean relatedOnlineLectureChecker( int lectureId ){
        OnlineLecture onlineLecture = onlineLectureRepository.findById( lectureId ).orElseThrow(
                () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
        );
        if( !userLectureRepository.findAllByLectureIdAndIsOnline( lectureId, 1 ).isEmpty() ) return false;
        if(fileRepository.findById( onlineLecture.getFile().getId() ).isPresent()) return false;
        return true;
    }

    // with offline lecture
    public boolean relatedOfflineLectureChecker( int lectureId ){
        OfflineLecture offlineLecture = offlineLectureRepository.findById( lectureId ).orElseThrow(
                () -> new CustomException(LectureErrorCode.OFFLINE_NOT_FOUND.getMessage())
        );
        if( !userLectureRepository.findAllByLectureIdAndIsOnline( lectureId, 0 ).isEmpty() ) return false;
        return true;
    }

    // with Category
    public boolean relatedCategoryChecker( int categoryId ){
        return true;
    }

    // with Day
    public boolean relatedDayChecker( int dayId ){
        return true;
    }
}
