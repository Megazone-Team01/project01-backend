package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Integer> {
    List<UserOrganization> findAllByUserId( int userId );
    List<UserOrganization> findAllByOrganizationId( int organizationId );

}
