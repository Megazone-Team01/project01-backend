package com.mzcteam01.mzcproject01be.domains.organization.service;

import com.mzcteam01.mzcproject01be.domains.lecture.repository.LectureRepository;
import com.mzcteam01.mzcproject01be.domains.organization.dto.request.GetOrganizationRequest;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.GetOrganizationLectureResponse;
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
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.organization.dto.response.AdminGetOrganizationResponse;

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
