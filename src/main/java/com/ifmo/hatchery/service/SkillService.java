package com.ifmo.hatchery.service;

import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository<Skill, Long> skillRepository;

    public List<Skill> findAll(){
        return skillRepository.findAll();
    }

    public List<Skill> findAllById(List<Long> skillIDs ){
        return skillRepository.findAllById(skillIDs);
    }

}
