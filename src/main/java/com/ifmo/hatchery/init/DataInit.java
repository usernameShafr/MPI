package com.ifmo.hatchery.init;

import com.example.demo.dao.PersonDAO;
import com.example.demo.entity.Person;
import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {

    private SkillRepository skills;



    @Autowired
    public DataInit(SkillRepository skills) {
        this.skills = skills;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = skills.count();

        if (count == 0) {
            Skill p1 = new Skill();

            p1.setName("RAB");


            //
            Person p2 = new Person();

            p2.setCasta("BETA");

            p2.setCount("56");
            p2.setStatus("treatment");

            skills.save(p1);
           // personDAO.save(p2);
        }

    }

}
