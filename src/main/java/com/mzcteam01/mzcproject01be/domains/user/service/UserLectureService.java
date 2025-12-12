package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserLectureResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLectureService {
    private final UserLectureRepository userLectureRepository;
    private final UserRepository userRepository;
    private final OnlineLectureRepository onlineRepository;
    private final OfflineLectureRepository offlineRepository;

    public void create(int userId, int lectureId, boolean isOnline, LocalDateTime registeredAt ){
        User user = userRepository.findById( userId ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다" ) );
        if( isOnline ) onlineRepository.findById( lectureId ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다") );
        else offlineRepository.findById( lectureId ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다") );

        UserLecture userLecture = UserLecture.builder()
                .user( user )
                .lectureId( lectureId )
                .isOnline( isOnline ? 1 : 0 )
                .registeredAt( registeredAt )
                .expiredAt( null )
                .build();
        userLectureRepository.save( userLecture );
    }

    public void update( int userLectureId, LocalDateTime expiredAt ){
        UserLecture userLecture = userLectureRepository.findById( userLectureId ).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) );
        userLecture.update( expiredAt );
    }

    public void delete( int userLectureId, int deletedBy ){
        UserLecture userLecture = userLectureRepository.findById( userLectureId ).orElseThrow( () -> new CustomException("해당 강의 등록이 존재하지 않습니다" ) );
        userLecture.delete( deletedBy );
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
}
