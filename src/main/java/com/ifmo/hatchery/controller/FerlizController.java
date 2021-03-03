package com.ifmo.hatchery.controller;


import com.ifmo.hatchery.model.system.Caste;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/service/ferliz")
public class FerlizController {

    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> allTasks = taskRepository.findAll();
        //List<Task> stageTasks = filterTaskByStages(allTasks, Stage.FERTILIZATION);

        model.addAttribute("allTasks", allTasks);
        return "/service/ferliz";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String createOrder(Model model,
                              @RequestParam(required = false,value = "updateStage") List<Long> updateStageID) {
        if(updateStageID == null || updateStageID.isEmpty()) {
            return "/home";
        }

        return "redirect:/service/ferliz";
    }


}
