package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final UserRepository userRepository;

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

}
