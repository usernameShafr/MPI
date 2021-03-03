package com.ifmo.hatchery.model.system;

import com.ifmo.hatchery.model.auth.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class TaskLock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private User handler;

    @Enumerated(EnumType.STRING)
    private TaskLockStatus lockStatus;

    @OneToOne(mappedBy = "taskLock")
    private Task task;
}
