package com.ifmo.hatchery.service;

import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.model.system.TaskLockStatus;
import com.ifmo.hatchery.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository<Task, Long> taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getStageTasks(Stage stage) {

        return taskRepository.findAllByStage(stage);
    }

    public List<Task> getAllByStageAndLock(Stage stage, TaskLockStatus status) {
        return taskRepository.findAllByStageAndLock(stage, TaskLockStatus.FAILED);
    }

    public Task getOneTask(Long taskId) {
        return taskRepository.getOne(taskId);
    }

    public List<Task> getAllByStageWithoutLock(Stage stage) {
        return taskRepository.findAllByStageWithoutLock(stage);
    }


}
