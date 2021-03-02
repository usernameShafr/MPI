package com.ifmo.hatchery.repository;


import com.ifmo.hatchery.model.system.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository<T extends Skill, ID extends Long> extends JpaRepository<Skill, Long> {

}
