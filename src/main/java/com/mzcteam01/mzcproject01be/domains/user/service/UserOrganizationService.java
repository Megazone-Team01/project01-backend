package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrganizationService {
    private final UserOrganizationRepository userOrganizationRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    public void create(int userId, int organizationId, LocalDateTime registeredAt, LocalDateTime expiredAt ){
        User user = userRepository.findById( userId ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다") );
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException("해당하는 아카데미가 존재하지 않습니다") );
        UserOrganization userOrganization = UserOrganization.builder()
                .user( user )
                .organization( organization )
                .registeredAt( registeredAt )
                .expiredAt( expiredAt )
                .build();
        userOrganizationRepository.save( userOrganization );
    }

    public void delete( int id, int deletedBy ){
        UserOrganization userOrganization = userOrganizationRepository.findById( id ).orElseThrow( () -> new CustomException("해당하는 소속 사용자를 찾을 수 없습니다") );
        userOrganization.delete( deletedBy );
    }

    public List<AdminGetUserOrganizationResponse> findAllByOrganizationId( int organizationId ){
        return userOrganizationRepository.findAllByOrganizationId( organizationId ).stream().map( AdminGetUserOrganizationResponse::of ).toList();
    }

    public List<AdminGetUserOrganizationResponse> findAllByUserId( int userId ){
        return userOrganizationRepository.findAllByUserId( userId ).stream().map( AdminGetUserOrganizationResponse::of ).toList();
    }

}
