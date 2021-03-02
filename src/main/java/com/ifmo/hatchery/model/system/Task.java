package com.ifmo.hatchery.model.system;


import lombok.Data;

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
    private Order order;

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

    private int amount;

    @Enumerated(EnumType.STRING)
    private Stage stage;
}
