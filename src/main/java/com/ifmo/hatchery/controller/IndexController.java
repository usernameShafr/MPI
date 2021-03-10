package com.ifmo.hatchery.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        return "redirect:/home";
    }
}
