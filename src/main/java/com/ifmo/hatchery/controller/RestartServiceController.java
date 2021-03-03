package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.*;
import com.ifmo.hatchery.repository.BiomaterialRepository;
import com.ifmo.hatchery.repository.TaskLockRepository;
import com.ifmo.hatchery.repository.TaskRepository;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("restartService")
public class RestartServiceController {

    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @Autowired
    private TaskLockRepository<TaskLock, Long> taskLockRepository;

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

        paramsMap.put("QUEUE", stageTasks.stream().filter(task -> task.getTaskLock() == null).count());
        paramsMap.put("IN_PROGRESS", stageTasks.stream().filter(task -> task.getTaskLock() != null && task.getTaskLock().getLockStatus() == TaskLockStatus.LOCKED).count());
        paramsMap.put("FAILED", stageTasks.stream().filter(task -> task.getTaskLock() != null && task.getTaskLock().getLockStatus() == TaskLockStatus.FAILED).count());
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
