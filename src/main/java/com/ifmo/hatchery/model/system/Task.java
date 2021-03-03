package com.ifmo.hatchery.model.system;


import com.ifmo.hatchery.model.auth.User;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Collection;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderX order;

    @OneToOne
    @JoinColumn(name = "male_material_id")
    private Biomaterial biomaterialMale;

    @OneToOne
    @JoinColumn(name = "female_material_id")
    private Biomaterial biomaterialFemale;

    @ManyToMany
    @JoinTable(
            name = "task_skills",
            joinColumns = @JoinColumn(
                    name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "skill_id", referencedColumnName = "id"))
    private Collection<Skill> skills;

    @Enumerated(EnumType.STRING)
    private Caste caste;

    private long amount;

    @Enumerated(EnumType.STRING)
    private Stage stage;

    @Enumerated(EnumType.STRING)
    private TaskLockStatus lockStatus;

    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true)
    @JoinColumn(name = "handler_id")
    private User lockUser;
}
