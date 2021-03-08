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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderX getOrder() {
        return order;
    }

    public void setOrder(OrderX order) {
        this.order = order;
    }

    public Biomaterial getBiomaterialMale() {
        return biomaterialMale;
    }

    public void setBiomaterialMale(Biomaterial biomaterialMale) {
        this.biomaterialMale = biomaterialMale;
    }

    public Biomaterial getBiomaterialFemale() {
        return biomaterialFemale;
    }

    public void setBiomaterialFemale(Biomaterial biomaterialFemale) {
        this.biomaterialFemale = biomaterialFemale;
    }

    public Collection<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Collection<Skill> skills) {
        this.skills = skills;
    }

    public Caste getCaste() {
        return caste;
    }

    public void setCaste(Caste caste) {
        this.caste = caste;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TaskLockStatus getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(TaskLockStatus lockStatus) {
        this.lockStatus = lockStatus;
    }

    public User getLockUser() {
        return lockUser;
    }

    public void setLockUser(User lockUser) {
        this.lockUser = lockUser;
    }
}





