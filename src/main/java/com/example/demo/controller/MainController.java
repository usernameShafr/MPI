package com.example.demo.controller;

import com.example.demo.form.PersonForm;
import com.example.demo.dao.PersonDAO;
import com.example.demo.entity.Person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class MainController {
 
    @Autowired
    private PersonDAO personDAO;
    
    private static List<Person> persons = new ArrayList<Person>();
    
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
 
    @Value("${error.message}")
    private String errorMessage;
 
    //@ResponseBody
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        //Iterable<Person> all = personDAO.findAll();
 
        //StringBuilder sb = new StringBuilder();
 
        //all.forEach(p -> sb.append(p.getCasta() + "<br>"));
        model.addAttribute("message", message);
        
        return "index";
    }
    
    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
 
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
 
        return "addPerson";
    }
    
    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, //
            @ModelAttribute("personForm") PersonForm personForm) {
 
        String casta = personForm.getCasta();
        String count = personForm.getCount();
 
        if (casta != null && casta.length() > 0 //
        		&& count != null  ) {
        	Person p4 = new Person();
        	 
            p4.setCasta(casta);
 
            p4.setCount(count);
            
            p4.setStatus("treatment");
            persons.add(p4);
            personDAO.save(p4);
           
            return "redirect:/index";
        }
 
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }
    
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {
    	Iterable<Person> all  = personDAO.findAll();
        model.addAttribute("persons", all);
 
        return "personList";
    }
}
