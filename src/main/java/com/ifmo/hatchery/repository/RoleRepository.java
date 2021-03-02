package com.ifmo.hatchery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifmo.hatchery.model.auth.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}