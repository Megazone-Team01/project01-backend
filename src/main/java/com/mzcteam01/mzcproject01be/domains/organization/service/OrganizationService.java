package com.mzcteam01.mzcproject01be.domains.organization.service;

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

    public List<GetOrganizationResponse> list(){
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map( GetOrganizationResponse::of ).toList();
    }

}
