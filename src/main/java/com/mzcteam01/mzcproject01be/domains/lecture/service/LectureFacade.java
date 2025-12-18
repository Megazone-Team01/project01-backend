package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.service.CategoryService;
import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import com.mzcteam01.mzcproject01be.common.base.day.repository.DayRepository;
import com.mzcteam01.mzcproject01be.common.exception.*;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.AdminCreateLectureRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
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

    @Transactional
    public List<AdminGetLectureResponse> getAllLecturesWithFilter( Integer isOnline, Integer status ) {
        List<AdminGetLectureResponse> results = new ArrayList<>();
        if( isOnline == null ) isOnline = 0;

        if( isOnline != 2 ) {
            List<OnlineLecture> onlineLectures = onlineRepository.findAll();
            for( OnlineLecture onlineLecture : onlineLectures ) results.add( AdminGetLectureResponse.of( onlineLecture, true ) );
        }
        if( isOnline != 1 ){
            List<OfflineLecture> offlineLectures = offlineRepository.findAll();
            for( OfflineLecture offlineLecture : offlineLectures ) results.add( AdminGetLectureResponse.of( offlineLecture, false ) );
        }
        if( status != null ){
            return onlineRepository.findAllByStatus( status ).stream().map( lecture -> AdminGetLectureResponse.of( lecture, true ) ).toList();
        }

        return results;
    }

    @Transactional
    public void create( AdminCreateLectureRequest request ) {
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
            OnlineLecture lecture = OnlineLecture.builder()
                    .name(request.getName())
                    .organization( organization )
                    .teacher( teacher )
                    .category( request.getCategory() )
                    .description( request.getDescription() )
                    .status( 0 )
                    .file( file )
                    .startAt( request.getStartAt() )
                    .endAt( request.getEndAt() )
                    .createdBy(teacher.getId())
                    .build();
            onlineRepository.save( lecture );
        }
        else if( request.getType() == 2 ){
            Room room = roomRepository.findById( request.getRoomId() ).orElseThrow(
                    () -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage())
            );
            Day dayValue = dayRepository.findByName( request.getDayValue() )
                    .orElseThrow( () -> new CustomException( DayErrorCode.DAY_NOT_FOUND.getMessage()) );
            String[] startTimeSplitter = request.getStartTimeAt().split(":");
            String[] endTimeSplitter = request.getEndTimeAt().split(":");
            String startTime = startTimeSplitter[0] + startTimeSplitter[1];
            String endTime = endTimeSplitter[0] + endTimeSplitter[1];
            OfflineLecture lecture = OfflineLecture.builder()
                    .name(request.getName())
                    .organization( organization )
                    .teacher( teacher )
                    .category( request.getCategory() )
                    .description( request.getDescription() )
                    .maxNum( request.getMaxNum() )
                    .room( room )
                    .startAt( request.getStartAt() )
                    .endAt( request.getEndAt() )
                    .startTimeAt( startTime )
                    .endTimeAt( endTime )
                    .day( dayValue.getValue() )
                    .createdBy(teacher.getId())
                    .build();
            offlineRepository.save( lecture );
        }
        else throw new CustomException(LectureErrorCode.LECTURE_TYPE_ERROR.getMessage());
    }

    @Transactional
    public void approve( int id ){
        OnlineLecture onlineLecture = onlineRepository.findById( id ).orElseThrow(
                () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
        );
        onlineLecture.updateStatus(1);
    }

    @Transactional
    public void reject( int id ){
        OnlineLecture onlineLecture = onlineRepository.findById( id ).orElseThrow(
                () -> new CustomException(LectureErrorCode.ONLINE_NOT_FOUND.getMessage())
        );
        onlineLecture.updateStatus(-1);
    }
}
