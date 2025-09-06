package com.nathan.sbecommerce.repository;

import com.nathan.sbecommerce.model.AppRole;
import com.nathan.sbecommerce.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(AppRole appRole);
}
