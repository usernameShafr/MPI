package com.ifmo.hatchery.repository;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.BioState;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.BiomaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiomaterialRepository <T extends Biomaterial, ID extends Long> extends JpaRepository<Biomaterial, Long> {
    List<Biomaterial> findByDonor(User donor);

    List<Biomaterial> findByTypeAndBioState(BiomaterialType type, BioState bioState);
}
