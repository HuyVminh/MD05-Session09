package com.ra.repository;

import com.ra.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String roleName);
}
