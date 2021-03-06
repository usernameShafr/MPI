package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.model.system.TaskLockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository<T extends Task, ID extends Long> extends JpaRepository<Task, Long> {
    List<Task> findAllByStage(Stage stage);
    List<Task> findAllByLockStatus(TaskLockStatus status);
    @Query("SELECT t FROM Task t WHERE t.stage = :stage and t.lockStatus IS NULL  ")
    List<Task> findAllByStageWithoutLock(@Param("stage") Stage stage);
    @Query("SELECT t FROM Task t WHERE t.stage = :stage and t.lockStatus = :lockStatus ")
    List<Task> findAllByStageAndLock(@Param("stage") Stage stage,@Param("lockStatus") TaskLockStatus status);

}
