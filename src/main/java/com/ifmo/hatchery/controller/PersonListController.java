package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PersonListController {
    @Autowired
    private UserService userService;
    //private static List<Person> persons = new ArrayList<Person>();
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {
        Iterable<UserX> all  = userService.allUsers();
        model.addAttribute("persons", all);

        return "personList";
    }
}
