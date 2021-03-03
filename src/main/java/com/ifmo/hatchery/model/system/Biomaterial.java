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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BiomaterialType getType() {
        return type;
    }

    public void setType(BiomaterialType type) {
        this.type = type;
    }

    public BioState getBioState() {
        return bioState;
    }

    public void setBioState(BioState bioState) {
        this.bioState = bioState;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }
}
