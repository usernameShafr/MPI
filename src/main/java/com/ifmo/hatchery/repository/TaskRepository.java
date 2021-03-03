package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.system.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository<T extends Task, ID extends Long> extends JpaRepository<Task, Long> {
}
