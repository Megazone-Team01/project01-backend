package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.OrganizationErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.CreateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.UpdateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.organization.repository.QOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService{
    private final OrganizationRepository organizationRepository;
    private final QOrganizationRepository qOrganizationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void create( CreateOrganizationRequest request ) {
        System.out.println( request.getOwnerId() );
        User owner = userRepository.findById( request.getOwnerId() ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        Organization organization = Organization.builder()
                .name( request.getName() )
                .webpage( request.getWebpage() )
                .owner( owner )
                .tel( request.getTel() )
                .addressCode( request.getAddressCode() )
                .addressDetail( request.getAddressDetail() )
                .isOnline( request.getIsOnline() )
                .description( request.getDescription() )
                .status( 0 )
                .createdBy( request.getOwnerId() )
                .build();
        organizationRepository.save( organization );
    }

    @Override
    @Transactional
    public void update(int id, UpdateOrganizationRequest request ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.update(
                request.getName(),
                request.getAddressCode(),
                request.getAddressDetail(),
                request.getTel(),
                request.getHomepage(),
                request.getType(),
                request.getDescription()
        );
    }

    @Override
    @Transactional
    public void approve( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.updateStatus( true );
    }

    @Override
    @Transactional
    public void reject( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.updateStatus( false );
    }

    @Override
    @Transactional
    public void delete( int id, int deletedBy ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.delete( deletedBy );
    }

    @Override
    public List<AdminGetOrganizationResponse> findAll(){
        return organizationRepository.findAll().stream().map( AdminGetOrganizationResponse::of).toList();
    }

    @Override
    public List<GetOrganizationResponse> list( GetOrganizationRequest request ) {
        return qOrganizationRepository.list( request.getName(), request.getStatusCode(), request.getIsOnline(), request.getName(), request.getOwnerId() )
                .stream().map( GetOrganizationResponse::of ).toList();
    }

    @Override
    public AdminGetOrganizationResponse findById( int id ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        return AdminGetOrganizationResponse.of( organization );
    }


}
