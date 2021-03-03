package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.Caste;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.repository.OrderXRepository;
import com.ifmo.hatchery.repository.SkillRepository;
import com.ifmo.hatchery.repository.TaskRepository;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private SkillRepository<Skill, Long> skillRepository;

    @Autowired
    private OrderXRepository<OrderX, Long> orderRepository;

    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @Autowired
    private UserRepository<User, Long> userRepository;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("availableSkills", skillRepository.findAll());
        return "/order";
    }

    @RequestMapping(value = { "/createOrder" }, method = RequestMethod.POST)
    public String createOrder(Model model,
                              Authentication authentication,
                             @RequestParam("caste") Caste caste,
                             @RequestParam("skills") List<Long> skillIDs) {
        if(caste == null) {
            model.addAttribute("errorMessage", "Caste isn't set");
            return "order";
        }

        if(skillIDs == null || skillIDs.isEmpty()) {
            model.addAttribute("errorMessage", "Skills aren't set");
            return "order";
        }
        OrderX order = new OrderX();
        order.setCaste(caste);
        List<Skill> skillsList = skillRepository.findAllById(skillIDs);
        order.setSkills(skillsList);

        User customer = userRepository.findByUsername(authentication.getName());
        order.setCustomer(customer);

        order = createTaskAndOrder(order);
        System.err.println(order);
        return "redirect:/order";
    }

    @Transactional
    private OrderX createTaskAndOrder(OrderX order) {
        order = orderRepository.save(order);
        Task task = new Task();
        task.setStage(Stage.START);
        task.setOrder(order);
        task = taskRepository.save(task);
        order.setTask(task);
        return orderRepository.save(order);
    }
}
