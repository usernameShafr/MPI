package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.BioState;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.BiomaterialType;
import com.ifmo.hatchery.model.system.Caste;
import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.model.system.TaskLockStatus;
import com.ifmo.hatchery.repository.BiomaterialRepository;
import com.ifmo.hatchery.repository.SkillRepository;
import com.ifmo.hatchery.repository.TaskRepository;
import com.ifmo.hatchery.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("task")
public class TaskController {
    private static final String REDIRECT_DASHBOARD = "redirect:/dashboard";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    @Autowired
    private UserRepository<UserX, Long> userRepository;

    @Autowired
    private BiomaterialRepository<Biomaterial, Long> biomaterialRepository;

    @Autowired
    private SkillRepository<Skill, Long> skillRepository;

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
        String answer =  commonGetProcessing(model, authentication, Stage.ADD_SKILLS);
        if(answer.equals("/task")) {
            model.addAttribute("availableSkills", skillRepository.findAll());
        }
        return answer;
    }

    private String commonGetProcessing(Model model, Authentication authentication, Stage stage) {
        UserX user = userRepository.findByUsername(authentication.getName());
        Optional<Task> task = getTaskWithLock(stage, user);
        if(task.isPresent()){
            System.err.println(task.get().getLockStatus() + ": " + task.get().getStage());
        } else {
            model.addAttribute(ERROR_ATTRIBUTE, String.format("Task for %s isn't found in queue", stage));
            System.err.println("Task not found");
            return REDIRECT_DASHBOARD;
        }
        model.addAttribute("taskForProcess", task.get());
        return "/task";
    }

    @RequestMapping(value = { "/processTask" }, method = RequestMethod.POST)
    public String processTask(Model model,
                              @RequestParam(value = "taskId", required = true) Long taskId,
                              @RequestParam(value = "maleBioMaterialId", required = false) Long maleBioMaterialId,
                              @RequestParam(value = "femaleBioMaterialId", required = false) Long femaleBioMaterialId,
                              @RequestParam(value = "caste", required = false) Caste caste,
                              @RequestParam(value = "skills", required = false) List<Long> skillIDs,
                              @RequestParam(value = "amount", required = false) Long amount
                              ) {
        Task task = taskRepository.getOne(taskId);
        if(task == null) {
            return returnToDashboardWithParams(REDIRECT_DASHBOARD, "Task with id " + taskId + " isn't found", "");
        }
        if(!validateParameters(model,
                task,
                maleBioMaterialId,
                femaleBioMaterialId,
                caste,
                skillIDs,
                amount)) {
            lockFailed(task);
            return returnToDashboardWithParams(REDIRECT_DASHBOARD, model.getAttribute(ERROR_ATTRIBUTE) + "", "");
        }
        boolean processSucceed = true;
        switch (task.getStage()) {
            case FERTILIZATION:
                processSucceed = processFertilization(model, task, maleBioMaterialId, femaleBioMaterialId);
                break;
            case CHOOSE_CASTE:
                processSucceed = processCaste(model, task, caste);
                break;
            case BOKANOVSKIY:
                processSucceed = processBokanovskiy(model, task, amount);
                break;
            case ADD_SKILLS:
                processSucceed = processAddSkills(model, task, skillIDs);
                break;
        }
        if(!processSucceed) {
            lockFailed(task);
            return returnToDashboardWithParams(REDIRECT_DASHBOARD, "" + model.getAttribute(ERROR_ATTRIBUTE), "");
        }
        nextStage(task);
        taskRepository.save(task);
        unlockTask(task);
        return returnToDashboardWithParams(REDIRECT_DASHBOARD, "", String.format("Task %s processed and moved to %s", task.getId(), task.getStage()));
    }

    @Transactional
    private boolean processFertilization(Model model, Task task, Long maleBioMaterialId, Long femaleBioMaterialId) {
        String message = "";
        Optional<Biomaterial> maleBio = biomaterialRepository.findById(maleBioMaterialId);
        Optional<Biomaterial> femaleBio = biomaterialRepository.findById(femaleBioMaterialId);
        if(!maleBio.isPresent()) {
            message += "Male bio material isn't found by id " + maleBioMaterialId;
        } else if (maleBio.get().getBioState() != BioState.NOT_USE) {
            message += "Selected male bio material already used " + maleBio.get().getFormSting();
        }
        if(!femaleBio.isPresent()) {
            message += "Female bio material isn't found by id " + femaleBioMaterialId;
        } else if(femaleBio.get().getBioState() != BioState.NOT_USE) {
            message += "Selected female bio material already used " + femaleBio.get().getFormSting();
        }
        if(!message.isEmpty()) {
            model.addAttribute(ERROR_ATTRIBUTE, message);
            return false;
        }
        maleBio.get().setBioState(BioState.USE);
        femaleBio.get().setBioState(BioState.USE);
        task.setBiomaterialMale(biomaterialRepository.save(maleBio.get()));
        task.setBiomaterialFemale(biomaterialRepository.save(femaleBio.get()));
        return true;
    }

    @Transactional
    private boolean processCaste(Model model, Task task, Caste casteFromRequest) {
        if(task.getOrder().getCaste() == casteFromRequest) {
            task.setCaste(casteFromRequest);
            taskRepository.save(task);
            return true;
        } else {
            model.addAttribute(ERROR_ATTRIBUTE, String.format("Requested caste isn't match original request.\nActual: %s\nExpected: %s",
                    casteFromRequest, task.getOrder().getCaste()));
            return false;
        }
    }

    @Transactional
    private boolean processBokanovskiy(Model model, Task task, Long amount) {
        Caste caste = task.getCaste();
        long maxAmount = caste.getMaxAmount();
        long minAmount = caste.getMinAmount();
        if(amount >= minAmount && amount <= maxAmount) {
            task.setAmount(amount);
            taskRepository.save(task);
            return true;
        }
        model.addAttribute(ERROR_ATTRIBUTE, String.format("Requested amount isn't match caste limits. Actual: %s\nExpected: [%s - %s]", amount, minAmount, maxAmount));
        return false;
    }

    private boolean processAddSkills(Model model, Task task, List<Long> skillIDs) {
        Collection<Skill> orderedSkills = task.getOrder().getSkills();
        List<Skill> requestedSkills = skillRepository.findAllById(skillIDs);
        Set<Skill> skillsSet = new HashSet<>(orderedSkills);
        skillsSet.addAll(requestedSkills);
        if(skillsSet.size() == orderedSkills.size() && skillsSet.size() == requestedSkills.size()){
            return true;
        }
        model.addAttribute(ERROR_ATTRIBUTE, String.format("Requested skills isn't match original request. Actual: %s\nExpected: %s", requestedSkills, orderedSkills));
        return false;
    }

    @SneakyThrows
    private String returnToDashboardWithParams(String redirectUrl, String errorMessage, String infoMessage) {
        String query = String.format("?errorMessage=%s&infoMessage=%s",
                URLEncoder.encode(errorMessage, "UTF-8"),
                URLEncoder.encode(infoMessage, "UTF-8"));

        return redirectUrl + query;
    }


    private boolean validateParameters(Model model,
                                       Task task,
                                       Long maleBioMaterialId,
                                       Long femaleBioMaterialId,
                                       Caste caste,
                                       List<Long> skillIDs,
                                       Long amount) {
        boolean validated = true;
        String message = "";
        System.err.println(String.format("Validate task request: {task_id=%s, maleBioMaterialId=%s, " +
                "femaleBioMaterialId=%s, caste=%s, skillIDs=%s, amount=%s}",
                task.getId(), maleBioMaterialId, femaleBioMaterialId, caste, skillIDs, amount));
        switch (task.getStage()) {
            case FERTILIZATION:
                if(maleBioMaterialId == null || maleBioMaterialId <= 0L) {
                    message += "'maleBioMaterialId' ins't exist in request\n";
                    validated = false;
                }
                if(femaleBioMaterialId == null || femaleBioMaterialId <= 0L) {
                    message += "'femaleBioMaterialId' ins't exist in request\n";
                    validated = false;
                }
                break;
            case CHOOSE_CASTE:
                if(caste == null) {
                    message += "'caste' ins't exist in request\n";
                    validated = false;
                }
                break;
            case BOKANOVSKIY:
                if(amount == null || amount <= 0L) {
                    message += "'amount' ins't exist in request\n";
                    validated = false;
                }
                break;
            case ADD_SKILLS:
                if(skillIDs == null || skillIDs.isEmpty()) {
                    message += "'skillIDs' ins't exist in request\n";
                    validated = false;
                }
                break;
        }
        if(!message.isEmpty()) {
            model.addAttribute(ERROR_ATTRIBUTE, String.format("%s: %s", task.getStage(), message));
        }
        return validated;
    }

    private void processParameters() {

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private synchronized Optional<Task> getTaskWithLock(Stage stage, UserX user) {
        List<Task> taskList = taskRepository.findAllByStageWithoutLock(stage);
        if(taskList.isEmpty()){
            return Optional.empty();
        }
        for(Task task : taskList) {
            if (task.getLockStatus() != TaskLockStatus.FAILED) {
            task.setLockStatus(TaskLockStatus.LOCKED);}
            task.setLockUser(user);
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    @Transactional
    private void lockFailed(Task task) {
        task.setLockStatus(TaskLockStatus.FAILED);
        taskRepository.save(task);
    }

    @Transactional
    private void unlockTask(Task task) {
        task.setLockStatus(null);
        taskRepository.save(task);
    }

    private void nextStage(Task task) {
        switch (task.getStage()) {
            case FERTILIZATION:
                task.setStage(Stage.CHOOSE_CASTE);
                return;
            case CHOOSE_CASTE:
                task.setStage(Stage.BOKANOVSKIY);
                return;
            case BOKANOVSKIY:
                task.setStage(Stage.ADD_SKILLS);
                return;
            case ADD_SKILLS:
            case FINISH:
                task.setStage(Stage.FINISH);
                return;
        }
        throw new IllegalStateException("Unexpected State");
    }

    public static boolean isStageAfter(Stage currentStage, Stage afterStage) {
        return currentStage.ordinal() > afterStage.ordinal();
    }
}
