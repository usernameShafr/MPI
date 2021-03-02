package com.ifmo.hatchery.repository;


import com.ifmo.hatchery.model.system.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
