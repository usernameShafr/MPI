package com.ifmo.hatchery.service;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.auth.Role;
import com.ifmo.hatchery.repository.RoleRepository;
import com.ifmo.hatchery.repository.UserRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository<UserX, Long> userRepository;
    @Autowired
    private RoleRepository <Role, Long> roleRepository;
    //@Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserX user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
    public UserX findByUsername(String username ){
        UserX user = userRepository.findByUsername(username);
        return user;
    }
    public UserX findUserById(Long userId) {
        Optional<UserX> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new UserX());
    }

    public List<UserX> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(UserX user) {
        UserX userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        //user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_DISPETCHER")));
        //user.setPassword("pas");
        userRepository.save(user);
        return true;
    }


    public List<UserX> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", UserX.class)
                .setParameter("paramId", idMin).getResultList();
    }
}