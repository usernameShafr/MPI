package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.model.system.TaskLockStatus;
import com.ifmo.hatchery.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    public static final String NOT_APPLICABLE = "N/A";

    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "infoMessage", required = false) String infoMessage,
                        @RequestParam(value = "errorMessage", required = false) String errorMessage) {

        List<Task> allTasks = taskRepository.findAll();
        Map<String, Object> paramsMap = new HashMap<>();

        //FERTILIZATION STAGE
        List<Task> stageTasks = filterTaskByStages(allTasks, Stage.FERTILIZATION);
        paramsMap.put("FERTILIZATION.QUEUE", stageTasks.stream().filter(task -> task.getLockStatus() == null).count());
        paramsMap.put("FERTILIZATION.IN_PROGRESS", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.LOCKED).count());
        //failedCount = fertilizationTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.FAILED).count();
        paramsMap.put("FERTILIZATION.FAILED", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.FAILED).count());
        paramsMap.put("FERTILIZATION.COMPLETED", filterTaskByStages(allTasks, Stage.CHOOSE_CASTE, Stage.BOKANOVSKIY, Stage.ADD_SKILLS, Stage.FINISH).size());
        //paramsMap.put("FERTILIZATION.STATUS", failedCount != 0L);
        //paramsMap.put("FERTILIZATION.GET_TASK", allTasks.size() - fertilizationTasks.size());

        //CHOOSE_CASTE STAGE
        stageTasks = filterTaskByStages(allTasks, Stage.CHOOSE_CASTE);
        paramsMap.put("CHOOSE_CASTE.QUEUE", stageTasks.stream().filter(task -> task.getLockStatus() == null).count());
        paramsMap.put("CHOOSE_CASTE.IN_PROGRESS", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.LOCKED).count());
        paramsMap.put("CHOOSE_CASTE.FAILED", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.FAILED).count());
        paramsMap.put("CHOOSE_CASTE.COMPLETED", filterTaskByStages(allTasks, Stage.BOKANOVSKIY, Stage.ADD_SKILLS, Stage.FINISH).size());

        //BOKANOVSKIY STAGE
        stageTasks = filterTaskByStages(allTasks, Stage.BOKANOVSKIY);
        paramsMap.put("BOKANOVSKIY.QUEUE", stageTasks.stream().filter(task -> task.getLockStatus() == null).count());
        paramsMap.put("BOKANOVSKIY.IN_PROGRESS", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.LOCKED).count());
        paramsMap.put("BOKANOVSKIY.FAILED", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.FAILED).count());
        paramsMap.put("BOKANOVSKIY.COMPLETED", filterTaskByStages(allTasks, Stage.ADD_SKILLS, Stage.FINISH).size());

        //ADD_SKILLS STAGE
        stageTasks = filterTaskByStages(allTasks, Stage.ADD_SKILLS);
        paramsMap.put("ADD_SKILLS.QUEUE", stageTasks.stream().filter(task -> task.getLockStatus() == null).count());
        paramsMap.put("ADD_SKILLS.IN_PROGRESS", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.LOCKED).count());
        paramsMap.put("ADD_SKILLS.FAILED", stageTasks.stream().filter(task -> task.getLockStatus() != null && task.getLockStatus() == TaskLockStatus.FAILED).count());
        paramsMap.put("ADD_SKILLS.COMPLETED", filterTaskByStages(allTasks, Stage.FINISH).size());

        //FINISH STAGE
        paramsMap.put("FINISH.QUEUE", NOT_APPLICABLE);
        paramsMap.put("FINISH.IN_PROGRESS", NOT_APPLICABLE);
        paramsMap.put("FINISH.FAILED", NOT_APPLICABLE);
        paramsMap.put("FINISH.COMPLETED", filterTaskByStages(allTasks, Stage.FINISH).size());

        model.addAttribute("TABLE_MAP", paramsMap);
        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }
        if(infoMessage != null){
            model.addAttribute("infoMessage", infoMessage);
        }
        return "/dashboard";
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
