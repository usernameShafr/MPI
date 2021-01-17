package com.ifmo.hatchery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("dispatcher")
public class DispatcherController {

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("some_attribute", null);
        //TODO: create navigation to TaskController and DispatcherController
        //"redirect:/task" or "redirect:/dashboard"
        return "index";
    }
}
