package com.ifmo.hatchery.controller;

import com.example.demo.entity.Person;
import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class personListController {
    @Autowired
    private UserRepository userRepository;
    //private static List<Person> persons = new ArrayList<Person>();
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {
        Iterable<User> all  = userRepository.findAll();
        model.addAttribute("persons", all);

        return "personList";
    }
}
