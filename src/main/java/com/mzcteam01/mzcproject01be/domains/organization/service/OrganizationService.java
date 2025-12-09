package com.mzcteam01.mzcproject01be.domains.organization.service;

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
