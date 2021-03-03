package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.system.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ifmo.hatchery.model.auth.Role;

public interface RoleRepository <T extends Role, ID extends Long>extends JpaRepository<Role, Long> {
}