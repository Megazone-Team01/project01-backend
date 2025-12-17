package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserOrganizationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface UserOrganizationService {
    public void create(int userId, int organizationId, LocalDateTime registeredAt, LocalDateTime expiredAt );
    public void delete( int id, int deletedBy );
    public List<AdminGetUserOrganizationResponse> findAllByOrganizationId(int organizationId );
    public List<AdminGetUserOrganizationResponse> findAllByUserId( int userId );
    public List<AdminGetUserOrganizationResponse> findAllTeacher();
}
