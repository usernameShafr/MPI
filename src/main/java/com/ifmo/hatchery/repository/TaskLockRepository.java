package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.system.TaskLock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLockRepository<T extends TaskLock, ID extends Long> extends JpaRepository<TaskLock, Long> {
}
