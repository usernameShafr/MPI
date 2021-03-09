package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.User;
import com.ifmo.hatchery.model.system.*;
import com.ifmo.hatchery.repository.*;
import com.ifmo.hatchery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("donor")
public class DonorController {

    @Autowired
    private BiomaterialRepository<Biomaterial, Long> biomaterialRepository;

    @Autowired
    private UserRepository<User, Long> userRepository;
    @Autowired
    private UserService userService;


    @RequestMapping(value = { "/", "/index","" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        model.addAttribute("some_attribute", null);
        System.err.println("ERRRRRRRRRRRRRRRRRRRRRRRROoooror");
        User donor = userService.findByUsername(authentication.getName());
        model.addAttribute("gender_attribute", donor.getBiomaterialType());
        System.err.println(donor.getBiomaterialType());
        return "donor";
    }

    @RequestMapping(value = { "/giveBio" }, method = RequestMethod.POST)
    public String giveBio     (Model model,
                              Authentication authentication,
                              @RequestParam("biomaterial") BiomaterialType biomaterial) {
        if(biomaterial == null) {
            model.addAttribute("errorMessage", "Biomaterial isn't set");
            return "donor";
        }

        Biomaterial biomaterials = new Biomaterial();
        biomaterials.setType(biomaterial);
        biomaterials.setBioState(BioState.NOT_USE);
        User donor = userService.findByUsername(authentication.getName());
        biomaterials.setDonor(donor);

        biomaterialRepository.save(biomaterials);
        // System.err.println(order);
        return "redirect:/donor";
    }
}
