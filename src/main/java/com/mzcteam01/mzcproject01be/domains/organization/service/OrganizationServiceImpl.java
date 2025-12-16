package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.OrganizationErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService{
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Override
    public void create( String name, String addressCode, String addressDetail, String tel, int ownerId ){
        User owner = userRepository.findById( ownerId ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
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

    @Override
    public void update( int id, String name, String addressCode, String addressDetail, String tel ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        organization.update( name, addressCode, addressDetail, tel );
    }

    @Override
    public void approve( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.updateStatus( true );
    }

    @Override
    public void reject( int organizationId ){
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        organization.updateStatus( false );
    }

    @Override
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
        return List.of();
    }

    @Override
    public AdminGetOrganizationResponse findById( int id ){
        Organization organization = organizationRepository.findById( id ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );
        return AdminGetOrganizationResponse.of( organization );
    }


}
