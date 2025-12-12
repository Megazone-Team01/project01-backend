package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.domains.lecture.repository.LectureRepository;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationLectureResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationTeacherResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    public List<GetOrganizationResponse> list(GetOrganizationRequest request ){
        /*
        추후 QueryDSL을 통한 Search Filter가 만들어지면 ownerId 조건과 다중 조건 추가
         */
        List<Organization> organizations;
        if( request.getStatusCode() == null ) organizations = organizationRepository.findAll();
        else organizations = organizationRepository.findByStatus( request.getStatusCode() );
        return organizations.stream().map( GetOrganizationResponse::of ).toList();
    }

    public GetOrganizationResponse findById( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElse( null );
        // 추후 커스텀 Exception으로 변경
        if( organization == null ) throw new RuntimeException("해당하는 기관이 존재하지 않습니다");

        return GetOrganizationResponse.of( organization );
    }

    @Transactional
    public List<GetOrganizationTeacherResponse> findOrganizationTeacher( int organizationId ){
        List<GetOrganizationTeacherResponse> result = new ArrayList<>();
        List<UserOrganization> userOrganizations = userOrganizationRepository.findAllByOrganizationId( organizationId );
        for( UserOrganization userOrganization : userOrganizations ){
            User user = userOrganization.getUser();
            // 추후 환경 변수나 다른 방법으로 이 부분을 고정?할 수 있는 방법 고려 필요
            if( user.getRole().getName().equals("선생님") ) result.add( GetOrganizationTeacherResponse.of( user ) );
        }
        return result;
    }

    @Transactional
    public List<GetOrganizationLectureResponse> findOrganizationLecture(int organizationId ){
        return lectureRepository.findAllByOrganizationId( organizationId ).stream().map( GetOrganizationLectureResponse::of ).toList();
    }

    private final UserRepository userRepository;

    public void create( String name, String addressCode, String addressDetail, String tel, int ownerId ){
        User owner = userRepository.findById( ownerId ).orElseThrow( () -> new CustomException("해당하는 사용자가 없습니다") );
        Organization organization = Organization.builder()
                .name( name )
                .addressCode( addressCode )
                .addressDetail( addressDetail )
                .tel( tel )
                .owner( owner )
                .status( 0 )
                .build();
        organizationRepository.save( organization );
    }

    public void update( int id, String name, String addressCode, String addressDetail, String tel ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.update( name, addressCode, addressDetail, tel );
    }

    public void approve( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.updateStatus( true );
    }

    public void reject( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.updateStatus( false );
    }

    public void delete( int id, int deletedBy ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.delete( deletedBy );
    }

    public List<AdminGetOrganizationResponse> findAll(){
        return organizationRepository.findAll().stream().map( AdminGetOrganizationResponse::of).toList();
    }

    public AdminGetOrganizationResponse findById( int id ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        return AdminGetOrganizationResponse.of( organization );
    }
}
