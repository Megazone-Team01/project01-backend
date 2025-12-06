package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    // private final UserRepository userRepository;

    public List<GetOrganizationResponse> list(GetOrganizationRequest request ){
        /*
        추후 QueryDSL을 통한 Search Filter가 만들어지면 ownerId 조건과 다중 조건 추가
         */
        List<Organization> organizations;
        if( request.getStatusCode() == null ) organizations = organizationRepository.findAll();
        else organizations = organizationRepository.findByStatus( request.getStatusCode() );
        return organizations.stream().map( GetOrganizationResponse::of ).toList();
    }

}
