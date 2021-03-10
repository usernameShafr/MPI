package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.BiomaterialType;
import com.ifmo.hatchery.service.BiomaterialService;
import com.ifmo.hatchery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("donor")
public class DonorController {

    @Autowired
    private BiomaterialService biomaterialService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = { "/", "/index","" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        model.addAttribute("some_attribute", null);
        //System.err.println("ERRRRRRRRRRRRRRRRRRRRRRRROoooror");
        UserX donor = userService.findByUsername(authentication.getName());
        model.addAttribute("gender_attribute", donor.getBiomaterialType());
        //System.err.println(donor.getBiomaterialType());
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
        UserX donor = userService.findByUsername(authentication.getName());

        biomaterialService.createBiomaterial(donor, biomaterial);
        return "redirect:/donor";
    }
}
