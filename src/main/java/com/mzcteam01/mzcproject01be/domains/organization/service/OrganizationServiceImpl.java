package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.common.exception.CommonErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.OrganizationErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.common.utils.CategoryConverter;
import com.mzcteam01.mzcproject01be.common.utils.RelatedEntityChecker;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureFacade;
import com.mzcteam01.mzcproject01be.domains.notification.service.NotificationService;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.CreateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.UpdateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationDetailResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.organization.repository.QOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService{
    private final OrganizationRepository organizationRepository;
    private final QOrganizationRepository qOrganizationRepository;
    private final UserRepository userRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final OnlineLectureRepository onlineLectureRepository;
    private final OfflineLectureRepository offlineLectureRepository;

    private final RelatedEntityChecker relatedEntityChecker;
    private final LectureFacade lectureFacade;
    private final CategoryConverter categoryConverter;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void create( CreateOrganizationRequest request, int createdBy ) {
        System.out.println( request.getOwnerId() );
        User owner = userRepository.findById( request.getOwnerId() ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        Organization organization = Organization.builder()
                .name( request.getName() )
                .webpage( request.getWebpage() )
                .owner( owner )
                .tel( request.getTel() )
                .address( request.getAddress() )
                .addressDetail( request.getAddressDetail() )
                .isOnline( request.getIsOnline() )
                .description( request.getDescription() )
                .status( 0 )
                .createdBy( createdBy )
                .build();
        organizationRepository.save( organization );
    }

    @Override
    @Transactional
    public void update(int id, UpdateOrganizationRequest request, int updatedBy ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.update(
                request.getAddress(),
                request.getAddressDetail(),
                request.getTel(),
                request.getHomepage(),
                request.getType(),
                request.getDescription()
        );
    }

    @Override
    @Transactional
    public void approve( int organizationId, int updatedBy ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.updateStatus( true );
    }

    @Override
    @Transactional
    public void reject( int organizationId, int updatedBy ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.updateStatus( false );
    }

    @Override
    @Transactional
    public void delete( int id, int deletedBy ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        if( !relatedEntityChecker.relatedOrganizationCheck( id ) ) throw new CustomException(CommonErrorCode.RELATED_ENTITY_EXISTED.getMessage());
        organization.delete( deletedBy );
    }

    @Override
    public List<AdminGetOrganizationResponse> findAll(){
        return organizationRepository.findAll().stream().map( AdminGetOrganizationResponse::of).toList();
    }

    @Override
    public List<GetOrganizationResponse> list( GetOrganizationRequest request ) {
        return qOrganizationRepository.list( request.getSearchString(), request.getStatusCode(), request.getIsOnline(), request.getName(), request.getOwnerId() )
                .stream().map( GetOrganizationResponse::of ).toList();
    }

    @Override
    public AdminGetOrganizationResponse findById( int id ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        return AdminGetOrganizationResponse.of( organization );
    }

    @Override
    @Transactional
    public AdminGetOrganizationDetailResponse getDetailById(int id) {
        Organization organization = organizationRepository.findById( id ).orElseThrow(
                () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage())
        );
        List<Lecture> lectures = new ArrayList<>();
        lectures.addAll( getAllOfflineLectureByOrganizationId( id ) );
        lectures.addAll( getAllOnlineLectureByOrganizationId( id ) );
        List<String> lectureResult = lectures.stream().map( Lecture::getName ).toList();

        List<String> students = new ArrayList<>();
        List<String> teachers = new ArrayList<>();

        List<UserOrganization> userOrganizations = userOrganizationRepository.findAllByOrganizationId( id );
        for( UserOrganization userOrganization : userOrganizations ) {
            User user = userOrganization.getUser();
            if( user.getRole().getName().equals("TEACHER") ) teachers.add( user.getName() );
            else if( user.getRole().getName().equals("STUDENT") ) students.add( user.getName() );
        }

        return AdminGetOrganizationDetailResponse.of( organization, lectureResult, students, teachers );
    }

    @Override
    public List<AdminGetLectureResponse> findLecturesByOrganizationId(int id) {
        List<Lecture> lectures = lectureFacade.getAllLecturesByOrganizationId( id );
        return lectures.stream().map( lecture -> {
            List<String> categoryLayer = categoryConverter.fullCodeToLayer( lecture.getCategory() );
            return AdminGetLectureResponse.of( lecture, null, categoryLayer );
        }).toList();
    }

    @Override
    public void apply(int organizationId, int userId) {
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow(
                () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage())
        );
        User user = userRepository.findById( userId ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()) );
        UserOrganization applyInfo = userOrganizationRepository.findByUserIdAndOrganizationId( userId, organizationId ).orElse( null );
        if( applyInfo != null ) throw new CustomException( UserErrorCode.USER_ALREADY_APPLIED.getMessage());

        UserOrganization target = UserOrganization.builder()
                .organization( organization )
                .user( user )
                .status( 0 )
                .registeredAt( LocalDateTime.now() )
                .expiredAt( LocalDateTime.now().plusMonths( 6L ) )
                .createdAt( LocalDateTime.now() )
                .createdBy( user.getId() )
                .build();
        userOrganizationRepository.save( target );

        // 알람 전달
        // 강사가 신청한 경우 -> 대표에게 알림
        if( user.getRole().getName().equals("TEACHER") ) {
            String message = String.format( "새로운 강사님이 기관에 등록 신청을 했습니다");
            notificationService.sendNotification( organization.getOwner().getId(), message );
        }
        // 학생이 신청한 경우 -> 강사들 + 대표에게 알림

    }

    private List<Lecture> getAllOnlineLectureByOrganizationId( int organizationId ) {
        // UserOrganization에서 소속된 Teacher 목록 조회
        List<UserOrganization> teachers = userOrganizationRepository.findAllByOrganizationId( organizationId );
        // Teacher들이 만든 Online Lecture 조회
        List<Lecture> result = new ArrayList<>();
        for( UserOrganization teacher : teachers ) {
            if( teacher.getUser().getRole().getName().equals( "TEACHER" ) ){
                int teacherId = teacher.getUser().getId();
                List<Lecture> lectures = onlineLectureRepository.findAllByTeacherId( teacherId );
                result.addAll( lectures );
            }
        }
        return result;
    }
    private List<Lecture> getAllOfflineLectureByOrganizationId( int organizationId ) {
// UserOrganization에서 소속된 Teacher 목록 조회
        List<UserOrganization> teachers = userOrganizationRepository.findAllByOrganizationId( organizationId );
        // Teacher들이 만든 Offline Lecture 조회
        List<Lecture> result = new ArrayList<>();
        for( UserOrganization teacher : teachers ) {
            if( teacher.getUser().getRole().getName().equals( "TEACHER" ) ){
                int teacherId = teacher.getUser().getId();
                List<Lecture> lectures = offlineLectureRepository.findAllByTeacherId( teacherId );
                result.addAll( lectures );
            }
        }
        return result;
    }


}
