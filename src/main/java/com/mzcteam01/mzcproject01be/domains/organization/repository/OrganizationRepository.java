package com.mzcteam01.mzcproject01be.domains.organization.repository;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
