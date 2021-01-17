package com.ifmo.hatchery.model.system;

import com.ifmo.hatchery.model.auth.User;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Collection;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToMany
    @JoinTable(
            name = "order_skills",
            joinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "skill_id", referencedColumnName = "id"))
    private Collection<Skill> skills;

    @Enumerated(EnumType.STRING)
    private Caste caste;

    @OneToOne(mappedBy = "order")
    private Task task;
}
