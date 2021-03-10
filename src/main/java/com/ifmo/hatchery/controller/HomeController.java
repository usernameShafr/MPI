package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.Role;
import com.ifmo.hatchery.model.auth.UserX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("home")
public class HomeController {
    @Autowired
    private com.ifmo.hatchery.service.UserService userService;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        UserX user = userService.findByUsername(authentication.getName());
        Set<Role> roles =user.getRoles();
        List<String> a = roles.stream().map(Role::getName).collect(Collectors.toList());

        System.err.println(user.getUsername());
        System.err.println(a.get(0));
        System.err.println("HOME_PAGE");
        model.addAttribute("role", a.get(0));
        return "/home";
    }

}
