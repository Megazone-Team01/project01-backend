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
public interface OrganizationService {
    void create( String name, String addressCode, String addressDetail, String tel, int ownerId );
    void update( int id, String name, String addressCode, String addressDetail, String tel );
    void approve( int organizationId );
    void reject( int organizationId );
    void delete( int id, int deletedBy );
    List<AdminGetOrganizationResponse> findAll();
    AdminGetOrganizationResponse findById( int id );
}
