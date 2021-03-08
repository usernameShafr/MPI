package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("home")
public class HomeController {
    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        List<Task> orders;
        orders = taskRepository.findAll();

        model.addAttribute("myOrders", orders);

        return "/home";
    }

}
