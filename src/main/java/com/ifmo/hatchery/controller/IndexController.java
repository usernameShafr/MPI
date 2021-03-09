package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserRepository<User, Long> userRepository;
    @Autowired
    private com.ifmo.hatchery.service.UserService userService;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        return "redirect:/home";
    }
}
