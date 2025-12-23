package com.mzcteam01.mzcproject01be.domains.organization.repository;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findByStatus( int status );

    List<Organization> findAllByStatus( int status );

    // deletedAt이 NULL인 것만
    List<Organization> findAllByDeletedAtIsNull();
}
