package com.ifmo.hatchery.service;

import com.ifmo.hatchery.model.system.BioState;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.BiomaterialType;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.repository.BiomaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiomaterialService {

    @Autowired
    private BiomaterialRepository<Biomaterial, Long> biomaterialRepository;

    public Biomaterial saveBiomaterial(Biomaterial biomaterial){

        biomaterial = biomaterialRepository.save(biomaterial);
            return biomaterial;
    }

   public List<Biomaterial> findByTypeAndBioState(BiomaterialType type, BioState bioState){
        return biomaterialRepository.findByTypeAndBioState(type,bioState);
   }
    public Optional<Biomaterial> findById(Long maleBioMaterialId){
        return biomaterialRepository.findById(maleBioMaterialId);
    }
}
