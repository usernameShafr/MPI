package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.BioState;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.BiomaterialType;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.model.system.TaskLock;
import com.ifmo.hatchery.model.system.TaskLockStatus;
import com.ifmo.hatchery.repository.BiomaterialRepository;
import com.ifmo.hatchery.repository.TaskLockRepository;
import com.ifmo.hatchery.repository.TaskRepository;
import com.ifmo.hatchery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @Autowired
    private TaskLockRepository<TaskLock, Long> taskLockRepository;

    @Autowired
    private UserRepository<User, Long> userRepository;

    @Autowired
    private BiomaterialRepository<Biomaterial, Long> biomaterialRepository;

    @RequestMapping(value = {"/fertilization" }, method = RequestMethod.GET)
    public String getFertilizationTask(Model model, Authentication authentication) {
        String answer = commonGetProcessing(model, authentication, Stage.FERTILIZATION);
        if(answer.equals("/task")) {
            model.addAttribute("maleBioMaterial", biomaterialRepository.findByTypeAndBioState(BiomaterialType.MALE, BioState.NOT_USE));
            model.addAttribute("femaleBioMaterial", biomaterialRepository.findByTypeAndBioState(BiomaterialType.FEMALE, BioState.NOT_USE));
        }
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
        User user = userRepository.findByUsername(authentication.getName());
        Optional<Task> task = getTaskWithLock(stage, user);
        if(task.isPresent()){
            System.err.println(task.get().getTaskLock().getId() + ": " + task.get().getStage());
        } else {
            System.err.println("Task not found");
            return "redirect:/dashboard";
        }
        model.addAttribute("taskForProcess", task.get());
        return "/task";
    }

    @RequestMapping(value = { "/processTask" }, method = RequestMethod.POST)
    public String processTask(Model model,
                              @RequestParam(value = "taskId", required = true) Long taskId) {
        Task task = taskRepository.getOne(taskId);
        /*switch (task.getStage()) {
            case FERTILIZATION:

        }*/
        System.err.println("Model: " + model.asMap());

        task = nextStage(task);
        taskRepository.save(task);
        unlockTask(task);

        return "redirect:/dashboard";
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private synchronized Optional<Task> getTaskWithLock(Stage stage, User user) {
        List<Task> taskList = taskRepository.findAllByStageWithoutLock(stage);
        if(taskList.isEmpty()){
            return Optional.empty();
        }
        TaskLock taskLock = new TaskLock();
        taskLock.setHandler(user);
        taskLock.setLockStatus(TaskLockStatus.LOCKED);
        for(Task task : taskList) {
            taskLock = taskLockRepository.saveAndFlush(taskLock);
            task.setTaskLock(taskLock);
            return Optional.of(taskRepository.saveAndFlush(task));
        }
        return Optional.empty();
    }

    @Transactional
    private void lockFailed(Task task) {
        TaskLock taskLock = task.getTaskLock();
        if(taskLock != null) {
            taskLock.setLockStatus(TaskLockStatus.FAILED);
            taskLockRepository.save(taskLock);
        }
    }

    @Transactional
    private void unlockTask(Task task) {
        TaskLock taskLock = task.getTaskLock();
        if(taskLock != null) {
            taskLockRepository.delete(taskLock);
            //TODO: remove it
            Task updatedTask = taskRepository.getOne(task.getId());
            System.err.println("Lock(" + updatedTask.getId() + "): " + updatedTask.getTaskLock());
        }
    }

    private Task nextStage(Task task) {
        switch (task.getStage()) {
            case FERTILIZATION:
                task.setStage(Stage.CHOOSE_CASTE);
                return task;
            case CHOOSE_CASTE:
                task.setStage(Stage.BOKANOVSKIY);
                return task;
            case BOKANOVSKIY:
                task.setStage(Stage.ADD_SKILLS);
                return task;
            case ADD_SKILLS:
            case FINISH:
                task.setStage(Stage.FINISH);
                return task;
        }
        throw new IllegalStateException("Unexpected State");
    }

    public static boolean isStageAfter(Stage currentStage, Stage afterStage) {
        return currentStage.ordinal() > afterStage.ordinal();
    }
}
