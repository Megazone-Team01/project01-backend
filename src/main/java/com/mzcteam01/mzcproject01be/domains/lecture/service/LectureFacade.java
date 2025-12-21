package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.service.CategoryService;
import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import com.mzcteam01.mzcproject01be.common.base.day.repository.DayRepository;
import com.mzcteam01.mzcproject01be.common.base.day.service.DayService;
import com.mzcteam01.mzcproject01be.common.exception.*;
import com.mzcteam01.mzcproject01be.common.utils.CategoryConverter;
import com.mzcteam01.mzcproject01be.common.utils.RelatedEntityChecker;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.AdminCreateLectureRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.AdminUpdateLectureRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureResponse;
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
import com.mzcteam01.mzcproject01be.domains.user.entity.UserLecture;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final OfflineLectureRepository offlineRepository;
    private final OnlineLectureRepository onlineRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final FileRepository fileRepository;
    private final RoomRepository roomRepository;
    private final DayRepository dayRepository;
    private final UserLectureRepository userLectureRepository;

    private final CategoryConverter categoryConverter;
    private final RelatedEntityChecker relatedEntityChecker;
    private final DayService dayService;

    @Transactional
    public List<AdminGetLectureResponse> getAllLecturesWithFilter( Integer isOnline, Integer status ) {
        List<AdminGetLectureResponse> results = new ArrayList<>();
        if( isOnline == null ) isOnline = 0;

        if( isOnline != 2 ) {
            List<OnlineLecture> onlineLectures = onlineRepository.findAll();
            for( OnlineLecture onlineLecture : onlineLectures ) {
                results.add( AdminGetLectureResponse.of( onlineLecture, true, categoryConverter.fullCodeToLayer( onlineLecture.getCategory() ) ) );
            }
        }
        if( isOnline != 1 ){
            List<OfflineLecture> offlineLectures = offlineRepository.findAll();
            List<String> alreadyIncluded = new ArrayList<>();
            for( OfflineLecture offlineLecture : offlineLectures ) {
                if( !alreadyIncluded.contains( offlineLecture.getName() ) ){
                    results.add( AdminGetLectureResponse.of( offlineLecture, false, categoryConverter.fullCodeToLayer( offlineLecture.getCategory() ) ) );
                    alreadyIncluded.add( offlineLecture.getName() );
                }
            }
        }
        if( status != null ){
            return onlineRepository.findAllByStatus( status ).stream().map( lecture -> AdminGetLectureResponse.of( lecture, true, categoryConverter.fullCodeToLayer( lecture.getCategory() ) ) ).toList();
        }

        return results;
    }

    @Transactional
    public void create( AdminCreateLectureRequest request, int createdBy ) {
        User teacher = userRepository.findById( request.getTeacherId() ).orElseThrow(
                () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage())
        );
        Organization organization = organizationRepository.findById( request.getOrganizationId() ).orElseThrow(
                () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage())
        );
        Category category = categoryService.categoryCodeToLayer( request.getCategory() ).get(0);
        if( request.getType() == 1 ){
            File file = fileRepository.findById( request.getFileId() ).orElseThrow(
                    () -> new CustomException(FileErrorCode.FILE_NOT_FOUND.getMessage())
            );
            String categoryFullCode = categoryConverter.singleToFullCode( request.getCategory() );
            OnlineLecture lecture = OnlineLecture.builder()
                    .name(request.getName())
                    .organization( organization )
                    .teacher( teacher )
                    .category( categoryFullCode )
                    .price( request.getPrice())
                    .description( request.getDescription() )
                    .status( 0 )
                    .file( file )
                    .startAt( request.getStartAt() )
                    .endAt( request.getEndAt() )
                    .createdBy( createdBy )
                    .build();
            onlineRepository.save( lecture );
        }
        else if( request.getType() == 2 ){
            Room room = roomRepository.findById( request.getRoomId() ).orElseThrow(
                    () -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage())
            );
            String[] startTimeSplitter = request.getStartTimeAt().split(":");
            String[] endTimeSplitter = request.getEndTimeAt().split(":");
            String startTime = startTimeSplitter[0] + startTimeSplitter[1];
            String endTime = endTimeSplitter[0] + endTimeSplitter[1];
            String categoryFullCode = categoryConverter.singleToFullCode( request.getCategory() );
            OfflineLecture.OfflineLectureBuilder lecture = OfflineLecture.builder()
                    .name(request.getName())
                    .organization( organization )
                    .teacher( teacher )
                    .category( categoryFullCode )
                    .description( request.getDescription() )
                    .maxNum( request.getMaxNum() )
                    .room( room )
                    .price( request.getPrice())
                    .startAt( request.getStartAt() )
                    .endAt( request.getEndAt() )
                    .startTimeAt( startTime )
                    .endTimeAt( endTime )
                    .createdBy( createdBy );
            for( String day : request.getDayValue() ){
                Day dayValue = dayRepository.findByName( day )
                        .orElseThrow( () -> new CustomException( DayErrorCode.DAY_NOT_FOUND.getMessage()) );
                lecture.day( dayValue.getValue() );
                offlineRepository.save( lecture.build() );
            }
        }
        else throw new CustomException(LectureErrorCode.LECTURE_TYPE_ERROR.getMessage());
    }

    @Transactional
    public void approve( int id, int updatedBy ){
        OnlineLecture onlineLecture = onlineRepository.findById( id ).orElseThrow(
                () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
        );
        onlineLecture.updateStatus(1);
    }

    @Transactional
    public void reject( int id, int updatedBy ){
        OnlineLecture onlineLecture = onlineRepository.findById( id ).orElseThrow(
                () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
        );
        onlineLecture.updateStatus(-1);
    }

    @Transactional
    public List<Lecture> getAllLearningLecture( int studentId ){
        List<UserLecture> lectures = userLectureRepository.findAllByUserId( studentId );
        List<Lecture> result = new ArrayList<>();
        for( UserLecture userLecture : lectures ){
            if( userLecture.getIsOnline() == 1 ){
                Lecture lecture = onlineRepository.findById( userLecture.getLectureId() ).orElseThrow(
                        () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
                );
                result.add( lecture );
            }
            else if( userLecture.getIsOnline() == 2 ){
                Lecture lecture = offlineRepository.findById( userLecture.getLectureId() ).orElseThrow(
                        () -> new CustomException(LectureErrorCode.OFFLINE_NOT_FOUND.getMessage())
                );
                result.add( lecture );
            }
        }
        return result;
    }

    @Transactional
    public List<Lecture> getAllTeachingLecture( int teacherId ){
        List<Lecture> result = onlineRepository.findAllByTeacherId( teacherId );
        List<Lecture> offline = offlineRepository.findAllByTeacherId( teacherId );
        result.addAll(offline);
        return result;
    }

    @Transactional
    public AdminGetLectureDetailResponse getLectureDetail(int id, boolean isOnline) {
        if( isOnline ){
            OnlineLecture lecture = onlineRepository.findById( id ).orElseThrow(
                    () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
            );
            return AdminGetLectureDetailResponse.ofOnline( lecture, categoryConverter.fullCodeToLayer(lecture.getCategory()) );
        }
        else {
            OfflineLecture lecture = offlineRepository.findById( id ).orElseThrow(
                    () -> new CustomException(LectureErrorCode.OFFLINE_NOT_FOUND.getMessage())
            );
            List<OfflineLecture> lectures = offlineRepository.findAllByNameAndOrganizationIdAndTeacherId(
                    lecture.getName(), lecture.getOrganization().getId(), lecture.getTeacher().getId()
            );
            List<String> days = new ArrayList<>();
            for( OfflineLecture dayLecture : lectures ){
                String day = dayRepository.findByValue( dayLecture.getDay() ).orElseThrow(
                        () -> new CustomException(DayErrorCode.DAY_NOT_FOUND.getMessage())
                ).getName();
                days.add( day );
            }
            return AdminGetLectureDetailResponse.ofOffline( lecture, days, categoryConverter.fullCodeToLayer(lecture.getCategory()) );
        }
    }

    @Transactional
    public void delete( int id, int deletedBy, boolean isOnline ){
        if( isOnline ){
            OnlineLecture lecture = onlineRepository.findById( id ).orElseThrow(
                    () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
            );
            if( !relatedEntityChecker.relatedOnlineLectureChecker( id ) ) throw new CustomException(CommonErrorCode.RELATED_ENTITY_EXISTED.getMessage());
            lecture.delete( deletedBy );
        }
        else {
            OfflineLecture lecture = offlineRepository.findById( id ).orElseThrow(
                    () -> new CustomException(LectureErrorCode.OFFLINE_NOT_FOUND.getMessage())
            );
            if( !relatedEntityChecker.relatedOfflineLectureChecker( id ) ) throw new CustomException(CommonErrorCode.RELATED_ENTITY_EXISTED.getMessage());
            List<OfflineLecture> dayLectures = offlineRepository.findAllByNameAndOrganizationIdAndTeacherId(
                    lecture.getName(), lecture.getOrganization().getId(), lecture.getTeacher().getId()
            );
            for( int i=0; i<dayLectures.size(); i++ ){
                if( i == 0 ) dayLectures.get(i).delete( deletedBy );
                else offlineRepository.delete( dayLectures.get(i) );
            }
        }
    }

    @Transactional
    public void updateLecture(int id, boolean isOnline, AdminUpdateLectureRequest request, int updatedBy ){
        if( isOnline ){
            OnlineLecture lecture = onlineRepository.findById( id ).orElseThrow( () -> new CustomException( LectureErrorCode.ONLINE_NOT_FOUND.getMessage() ) );
            lecture.update( request.getName(), null, request.getPrice(), request.getStartAt(), request.getEndAt(), request.getDescription(), null );
        }
        else {
            OfflineLecture lecture = offlineRepository.findById( id ).orElseThrow( () -> new CustomException( LectureErrorCode.OFFLINE_NOT_FOUND.getMessage() ) );
            List<OfflineLecture> lectures = offlineRepository.findAllByNameAndOrganizationIdAndTeacherId(
                    lecture.getName(), lecture.getOrganization().getId(), lecture.getTeacher().getId()
            );
            // 요일들 수정
            List<Integer> dayValues = new ArrayList<>();
            for( String dayName : request.getDays() ){
                int dayValue = dayService.nameToValue( dayName );
                dayValues.add( dayValue );
            }
            // Case 1. DayValues == Lectures
            if( dayValues.size() == lectures.size() ){
                for( int i=0; i<lectures.size(); i++ ){
                    OfflineLecture offlineLecture = lectures.get(i);
                    offlineLecture.update(
                            request.getName(), null, request.getPrice(), request.getStartAt(), request.getEndAt(), request.getDescription(),
                            request.getMaxNum(), null, request.getStartTimeAt(), request.getEndTimeAt(), dayValues.get(i)
                    );
                }
            }
            // Case 2. DayValues > Lectures
            else if( dayValues.size() > lectures.size() ){
                for( int i=0; i<lectures.size(); i++ ){
                    OfflineLecture offlineLecture = lectures.get(i);
                    offlineLecture.update(
                            request.getName(), null, request.getPrice(), request.getStartAt(), request.getEndAt(), request.getDescription(),
                            request.getMaxNum(), null, request.getStartTimeAt(), request.getEndTimeAt(), dayValues.get(i)
                    );
                }
                int more = dayValues.size() - lectures.size();
                OfflineLecture.OfflineLectureBuilder tempBuilder = OfflineLecture.builder()
                        .name(request.getName())
                        .organization( lecture.getOrganization() )
                        .teacher( lecture.getTeacher() )
                        .category( lecture.getCategory() )
                        .description( request.getDescription() )
                        .price( request.getPrice() )
                        .maxNum( request.getMaxNum() )
                        .room( lecture.getRoom() )
                        .startAt( request.getStartAt() )
                        .endAt( request.getEndAt() )
                        .startTimeAt( request.getStartTimeAt() )
                        .endTimeAt( request.getEndTimeAt() )
                        .createdBy( updatedBy );
                for( int i=0; i<more; i++ ){
                    tempBuilder.day( dayValues.get( dayValues.size() - i - 1 ) );
                    offlineRepository.save( tempBuilder.build() );
                }
            }
            // Case 3. DayValues < Lectures
            else {
                for (int i = 0; i < dayValues.size(); i++) {
                    OfflineLecture offlineLecture = lectures.get(i);
                    offlineLecture.update(
                            request.getName(), null, request.getPrice(), request.getStartAt(), request.getEndAt(), request.getDescription(),
                            request.getMaxNum(), null, request.getStartTimeAt(), request.getEndTimeAt(), dayValues.get(i)
                    );
                }
                for (int i = dayValues.size(); i < lectures.size(); i++) {
                    OfflineLecture temp = lectures.get(i);
                    offlineRepository.delete(temp);
                }
            }
        }
    }
}
