package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.model.system.TaskLockStatus;
import com.ifmo.hatchery.repository.BiomaterialRepository;
import com.ifmo.hatchery.repository.TaskRepository;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("restartService")
public class RestartServiceController {

    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @Autowired
    private UserRepository<User, Long> userRepository;

    @Autowired
    private BiomaterialRepository<Biomaterial, Long> biomaterialRepository;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        return "restartService";
    }

    @RequestMapping(value = {"/fertilization" }, method = RequestMethod.GET)
    public String getFertilizationTask(Model model, Authentication authentication) {
        String answer = commonGetProcessing(model, authentication, Stage.FERTILIZATION);
        return answer;
    }

    @RequestMapping(value = {"/choose_caste" }, method = RequestMethod.GET)
    public String getChooseCasteTask(Model model, Authentication authentication) {
        return commonGetProcessing(model, authentication, Stage.CHOOSE_CASTE);
    }

    @RequestMapping(value = {"/bokanovskiy" }, method = RequestMethod.GET)
    public String getBokanovskiyTask(Model model, Authentication authentication) {
        return commonGetProcessing(model, authentication, Stage.BOKANOVSKIY);
    }

    @RequestMapping(value = {"/add_skills" }, method = RequestMethod.GET)
    public String getAddSkillsTask(Model model, Authentication authentication) {
        return commonGetProcessing(model, authentication, Stage.ADD_SKILLS);
    }
    private String commonGetProcessing(Model model, Authentication authentication, Stage stage) {
        List<Task> stageTasks = taskRepository.findAllByStage(stage);
        List<Task> allTasks = taskRepository.findAll();
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("QUEUE", stageTasks.stream().filter(task -> task.getLockStatus() == null).count());
        paramsMap.put("IN_PROGRESS", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.LOCKED).count());
        paramsMap.put("FAILED", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.FAILED).count());
        switch (stage) {
            case FERTILIZATION:
                paramsMap.put("COMPLETED", filterTaskByStages(allTasks, Stage.CHOOSE_CASTE, Stage.BOKANOVSKIY, Stage.ADD_SKILLS, Stage.FINISH).size());
            case CHOOSE_CASTE:
                paramsMap.put("COMPLETED", filterTaskByStages(allTasks, Stage.BOKANOVSKIY, Stage.ADD_SKILLS, Stage.FINISH).size());
            case BOKANOVSKIY:
                paramsMap.put("COMPLETED", filterTaskByStages(allTasks,Stage.ADD_SKILLS, Stage.FINISH).size());
            case ADD_SKILLS:
                paramsMap.put("COMPLETED", filterTaskByStages(allTasks, Stage.FINISH).size());
        }

        model.addAttribute("TABLE_MAP", paramsMap);
        model.addAttribute("stage1", stage);

        return "/restartService";
    }
    @PostMapping("/restart" )
    public String restartService(Model model, @RequestParam() Stage stage2) {
        System.err.println(stage2.toString().toLowerCase());

        List<Task> statusTasks = taskRepository.findAllByLockStatus(TaskLockStatus.FAILED);
        for(Task task : statusTasks) {
            task.setLockStatus(null);
            taskRepository.save(task);
        }
        statusTasks = taskRepository.findAllByLockStatus(TaskLockStatus.LOCKED);
        for(Task task : statusTasks) {
            task.setLockStatus(null);
            taskRepository.save(task);
        }

        return "redirect:/restartService/"+stage2.toString().toLowerCase();

    }

    public static boolean hasFailures(Object status){
        if(status instanceof Long) {
            long failedCount = (Long) status;
            return failedCount > 0L;
        } else {
            return false;
        }
    }

    private static List<Task> filterTaskByStages(List<Task> taskList, Stage... stages) {
        List<Stage> stageList = Arrays.asList(stages);
        return taskList.stream()
                .filter(task -> stageList.contains(task.getStage()))
                .collect(Collectors.toList());
    }
}
