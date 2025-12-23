package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.UserStatusResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.exception.LectureException;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserLectureResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLectureService {
    private final UserLectureRepository userLectureRepository;
    private final UserRepository userRepository;
    private final OnlineLectureRepository onlineRepository;
    private final OfflineLectureRepository offlineRepository;
    private final QOfflineLectureRepository qOfflineLectureRepository;


    public void create(int userId, int lectureId, boolean isOnline, LocalDateTime registeredAt ){
        User user = userRepository.findById( userId ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        if( isOnline ) onlineRepository.findById( lectureId ).orElseThrow( () -> new LectureException(LectureErrorCode.ONLINE_NOT_FOUND) );
        else offlineRepository.findById( lectureId ).orElseThrow( () -> new LectureException(LectureErrorCode.ONLINE_NOT_FOUND));
        boolean exists = userLectureRepository.existsByUserIdAndLectureId(userId, lectureId);

        UserStatusResponse status = qOfflineLectureRepository.status(user.getId());

        if(exists){
            log.info("exists lecture applied {} ", "이미 신청한 강의 존재합니다.");
            throw new LectureException(LectureErrorCode.APPLIED_LECTURE);
        }

        if(status.getStatus() != 1){
            log.error("UserLectureService.create: {} {}", status.getStatus(), "기관에 승인되지 않습니다.! 승인을 요청하세요!");
            throw new LectureException(LectureErrorCode.STATUS_NOT_APPROVED);
        }

        UserLecture userLecture = UserLecture.builder()
                .user( user )
                .lectureId( lectureId )
                .isOnline( isOnline ? 1 : 0 )
                .registeredAt( registeredAt )
                .expiredAt( null )
                .build();

        userLecture.createdBy(user.getId());
        userLecture.update(LocalDateTime.now().plusMonths(1));
        log.info("\uD83D\uDCCC success applied");
        userLectureRepository.save( userLecture );
    }

    public void update( int userLectureId, LocalDateTime expiredAt ){
        UserLecture userLecture = userLectureRepository.findById( userLectureId ).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) );
        userLecture.update( expiredAt );
    }

    public void delete( int userId, int lectureId, boolean isOnline){
        log.info("userLectureId {}", lectureId);
        UserLecture userLecture = userLectureRepository.findByUserIdAndLectureId(  userId, lectureId );
        if( userLecture == null ){
            if (!isOnline) throw new LectureException(LectureErrorCode.OFFLINE_NOT_FOUND);
            else throw new LectureException(LectureErrorCode.ONLINE_NOT_FOUND);
        }
        log.info("userId {}", userId);
        userLectureRepository.delete( userLecture );
    }

    public List<AdminGetUserLectureResponse> findAll(){
        return userLectureRepository.findAll().stream().map( userLecture -> {
            String lectureName;
            if( userLecture.getIsOnline() == 1 ) lectureName = onlineRepository.findById(userLecture.getLectureId()).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) ).getName();
            else lectureName = offlineRepository.findById(userLecture.getLectureId()).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) ).getName();
            return AdminGetUserLectureResponse.of( userLecture, lectureName );
        }).toList();
    }

    public AdminGetUserLectureResponse findById( int id ){
        UserLecture userLecture = userLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) );
        String lectureName;
        if( userLecture.getIsOnline() == 1 ) lectureName = onlineRepository.findById(userLecture.getLectureId()).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) ).getName();
        else lectureName = offlineRepository.findById(userLecture.getLectureId()).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) ).getName();
        return AdminGetUserLectureResponse.of( userLecture, lectureName );
    }

    public boolean UserAppliedLecture(int userId, int lectureId){
        return userLectureRepository.existsByUserIdAndLectureId(userId, lectureId);
    }
}
