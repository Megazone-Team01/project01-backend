package com.mzcteam01.mzcproject01be.domains.lecture;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import com.mzcteam01.mzcproject01be.common.base.day.repository.DayRepository;
import com.mzcteam01.mzcproject01be.common.base.day.service.DayService;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetOfflineLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetOnlineLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureRepository {
    private final OnlineLectureRepository onlineLectureRepository;
    private final OfflineLectureRepository offlineLectureRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RoomRepository roomRepository;
    private final FileRepository fileRepository;
    private final DayService dayService;

    public void createOnline(
            String name,
            int organizationId,
            int teacherId,
            int categoryId,
            int price,
            LocalDateTime startAt,
            LocalDateTime endAt,
            String description,
            int fileId
    ){
        Organization organization = organizationRepository.findById(organizationId).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        User teacher = userRepository.findById( teacherId ).orElseThrow( () -> new CustomException("해당하는 선생님이 존재하지 않습니다") );
        Category category = categoryRepository.findById( categoryId ).orElseThrow( () -> new CustomException("잘못된 분류입니다") );
        File file = fileRepository.findById( fileId ).orElseThrow( () -> new CustomException("해당하는 파일이 존재하지 않습니다") );
        Category root = category;
        StringBuilder categoryLine = new StringBuilder();
        while( root != null ){
            categoryLine.insert(0, root.getName());
            root = root.getParent();
        }
        OnlineLecture lecture = OnlineLecture.builder()
                .name( name )
                .organization( organization )
                .teacher( teacher )
                .category(categoryLine.toString())
                .price( price )
                .startAt( startAt )
                .endAt( endAt )
                .description( description )
                .file( file )
                .status( 0 )
                .build();
        onlineLectureRepository.save( lecture );
    }

    public void createOffline(
            String name,
            int organizationId,
            int teacherId,
            int categoryId,
            int price,
            LocalDateTime startAt,
            LocalDateTime endAt,
            String description,
            int maxNum,
            int roomId,
            String startTimeAt,
            String endTimeAt,
            int dayValue
    ){
        Organization organization = organizationRepository.findById(organizationId).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        User teacher = userRepository.findById( teacherId ).orElseThrow( () -> new CustomException("해당하는 선생님이 존재하지 않습니다") );
        Category category = categoryRepository.findById( categoryId ).orElseThrow( () -> new CustomException("잘못된 분류입니다") );
        Room room = roomRepository.findById( roomId ).orElseThrow( () -> new CustomException("해당하는 스터디룸이 존재하지 않습니다") );
        Category root = category;
        StringBuilder categoryLine = new StringBuilder();
        while( root != null ){
            categoryLine.insert(0, root.getName());
            root = root.getParent();
        }
        OfflineLecture lecture = OfflineLecture.builder()
                .name( name )
                .organization( organization )
                .teacher( teacher )
                .category(categoryLine.toString())
                .price( price )
                .startAt( startAt )
                .endAt( endAt )
                .description( description )
                .maxNum( maxNum )
                .room( room )
                .startTimeAt( startTimeAt )
                .endTimeAt( endTimeAt )
                .day( dayValue )
                .build();
        offlineLectureRepository.save( lecture );
    }

    public void updateOnlineLecture( int id, String name, Integer teacherId, Integer price, LocalDateTime startAt, LocalDateTime endAt, String description, File file ){
        User teacher = null;
        if( teacherId != null ) teacher = userRepository.findById( teacherId ).orElseThrow( () -> new CustomException("해당하는 강사가 존재하지 않습니다") );
        OnlineLecture lecture = onlineLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다") );
        lecture.update( name, teacher, price, startAt, endAt, description, file );
    }

    public void updateOfflineLecture( int id, String name, Integer teacherId, Integer price, LocalDateTime startAt, LocalDateTime endAt, String description,
                                      Integer maxNum, Integer roomId, String startTimeAt, String endTimeAt, Integer dayValue)
    {
        User teacher = null;
        if( teacherId != null ) teacher = userRepository.findById( teacherId ).orElseThrow( () -> new CustomException("해당하는 강사가 존재하지 않습니다") );
        Room room = null;
        if( roomId != null ) room = roomRepository.findById( roomId ).orElseThrow( () -> new CustomException("해당하는 강의실이 존재하지 않습니다" ) );
        OfflineLecture lecture = offlineLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다") );
        lecture.update( name, teacher, price, startAt, endAt, description, maxNum, room, startTimeAt, endTimeAt, dayValue );
    }

    public void deleteLecture( boolean isOnline, int id, int deletedBy ){
        Lecture lecture;
        if( isOnline ) lecture = onlineLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다"));
        else lecture = offlineLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다"));
        lecture.delete( deletedBy );
    }

    public List<AdminGetLectureResponse> findAllLecture() {
        List<AdminGetLectureResponse> result = new ArrayList<>();
        // 오프라인 강의
        for( Lecture lecture : offlineLectureRepository.findAll() )result.add( AdminGetLectureResponse.of( lecture, false ) );
        for( Lecture lecture : onlineLectureRepository.findAll() )result.add( AdminGetLectureResponse.of( lecture, true ) );
        return result;
    }

    public AdminGetOnlineLectureResponse findOnlineLectureById( int id ){
        OnlineLecture lecture = onlineLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다") );
        return AdminGetOnlineLectureResponse.of( lecture );
    }

    public AdminGetOfflineLectureResponse findOfflineLectureById(int id ){
        OfflineLecture lecture = offlineLectureRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 강의가 존재하지 않습니다") );
        String day = dayService.valueToName( lecture.getDay() );
        return AdminGetOfflineLectureResponse.of( lecture, day );
    }


}
