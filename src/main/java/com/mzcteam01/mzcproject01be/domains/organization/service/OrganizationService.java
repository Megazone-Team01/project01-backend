package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.domains.organization.dto.request.CreateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.UpdateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationDetailResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;

import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;

import java.util.List;

public interface OrganizationService {
    void create( CreateOrganizationRequest request );
    void update( int id, UpdateOrganizationRequest request );
    void approve( int organizationId );
    void reject( int organizationId );
    void delete( int id, int deletedBy );
    List<AdminGetOrganizationResponse> findAll();
    List<GetOrganizationResponse> list( GetOrganizationRequest request );
    AdminGetOrganizationResponse findById( int id );
    AdminGetOrganizationDetailResponse getDetailById( int id );
}
