package com.ifmo.hatchery.init;

import com.example.demo.entity.Person;
import com.ifmo.hatchery.model.auth.Role;
import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.repository.RoleRepository;
import com.ifmo.hatchery.repository.SkillRepository;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInit implements ApplicationRunner {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SkillRepository<Skill, Long> skillRepository;
    @Autowired
    private UserRepository<User, Long> userRepository;
    @Autowired
    private RoleRepository<Role, Long> roleRepository;

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

            Role role = new Role();
            role.setName("ROLE_CUSTOMER");
            roleRepository.save(role);

            User user = new User();
            user.setEmail("email@gmail.com");
            user.setUsername("username");
            user.setLastName("lastname");
            user.setPassword(bCryptPasswordEncoder.encode("password"));
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);

            role = new Role();
            role.setName("ROLE_DISPATCHER");
            roleRepository.save(role);

            user = new User();
            user.setEmail("email1@gmail.com");
            user.setUsername("username1");
            user.setLastName("lastname1");
            //user.setPassword("password");
            user.setPassword(bCryptPasswordEncoder.encode("password"));
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);

            role = new Role();
            role.setName("ROLE_DONOR");
            roleRepository.save(role);

            user = new User();
            user.setEmail("email11@gmail.com");
            user.setUsername("usernameDonor");
            user.setLastName("lastnameDonor");
            user.setPassword(bCryptPasswordEncoder.encode("password"));
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);
        }

    }

}
