package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.service.OrderService;
import com.ifmo.hatchery.service.SkillService;
import com.ifmo.hatchery.service.UserService;
import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.Caste;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.model.system.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private SkillService skillService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication,@RequestParam(value = "errorMessage", required = false) String errorMessage) {
        UserX customer = userService.findByUsername(authentication.getName());
        List<OrderX> orders = orderService.findByCustomer(customer);
        model.addAttribute("myOrders", orders);
        if (errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }
        model.addAttribute("availableSkills", skillService.findAll());
        return "order";
    }

    @RequestMapping(value = { "/createOrder" }, method = RequestMethod.POST)
    public String createOrder(Model model,
                              Authentication authentication,
                             @RequestParam("caste") Caste caste,
                             @RequestParam(value = "skills", required = false) List<Long> skillIDs) {
        if(caste == null) {
            model.addAttribute("errorMessage", "Caste isn't set");
            return "order";
        }

        if(skillIDs == null || skillIDs.isEmpty()) {
            model.addAttribute("errorMessage", "Skills aren't set");
            return "redirect:/order?errorMessage=Skills%20are%20not%20set";
        }
        UserX customer = userService.findByUsername(authentication.getName());
        List<Skill> skillsList = skillService.findAllById(skillIDs);

        orderService.createOrder(caste, skillsList, customer);
        return "redirect:/order";
    }


}
