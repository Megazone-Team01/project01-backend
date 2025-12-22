package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.AdminGetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.CreateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.UpdateOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationDetailResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationResponse;

import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.OrganizationWithRoomsResponse;

import java.util.List;

public interface OrganizationService {
    void create( CreateOrganizationRequest request, int createdBy );
    void update( int id, UpdateOrganizationRequest request, int updatedBy );
    void approve( int organizationId, int updatedBy );
    void reject( int organizationId, int updatedBy );
    void delete( int id, int deletedBy );
    List<AdminGetOrganizationResponse> findAll();
    List<GetOrganizationResponse> list( GetOrganizationRequest request );
    AdminGetOrganizationResponse findById( int id );
    AdminGetOrganizationDetailResponse getDetailById( int id );

    List<AdminGetLectureResponse> findLecturesByOrganizationId( int id );
    List<OrganizationWithRoomsResponse> getOrganizationsWithRooms();
}
