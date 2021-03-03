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
import javax.persistence.ManyToOne;

@Entity
@Data
public class Biomaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BiomaterialType type;

    @Enumerated(EnumType.STRING)
    private BioState bioState;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    public String getFormSting() {
        return String.format("id=%s, type=%s, state=%s, donor={username=%s, lastname=%s}", id, type, bioState, donor.getUsername(), donor.getLastName());
    }
}
