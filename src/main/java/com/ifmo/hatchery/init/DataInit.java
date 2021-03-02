package com.ifmo.hatchery.init;

import com.example.demo.entity.Person;
import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {

    @Autowired
    private SkillRepository<Skill, Long> skillRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = skillRepository.count();

        if (count == 0) {
            Skill skill = new Skill();
            skill.setName("Stamina");
            skillRepository.save(skill);

            skill = new Skill();
            skill.setName("Heat resistance");
            skillRepository.save(skill);

            skill = new Skill();
            skill.setName("Singer");
            skillRepository.save(skill);



            //
            Person p2 = new Person();

            p2.setCasta("BETA");

            p2.setCount("56");
            p2.setStatus("treatment");

           // personDAO.save(p2);
        }

    }

}
