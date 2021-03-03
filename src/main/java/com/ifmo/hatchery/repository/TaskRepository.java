package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository<T extends Task, ID extends Long> extends JpaRepository<Task, Long> {
    List<Task> findAllByStage(Stage stage);
}
