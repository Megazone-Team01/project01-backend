package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByName(String name);
}
