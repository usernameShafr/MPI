package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository<T extends Task, ID extends Long> extends JpaRepository<Task, Long> {
    List<Task> findAllByStage(Stage stage);

    @Query("SELECT t FROM Task t WHERE t.stage = :stage and t.taskLock IS NOT NULL")
    List<Task> findAllByStageWithoutLock(@Param("stage") Stage stage);
}
