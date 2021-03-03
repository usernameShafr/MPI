package com.example.demo.init;

 
import com.example.demo.dao.PersonDAO;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
 
@Component
public class DataInit implements ApplicationRunner {
 
    private PersonDAO personDAO;
 
 
 
    @Autowired
    public DataInit(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = personDAO.count();
 
        if (count == 0) {
            Person p1 = new Person();
 
            p1.setCasta("ALFA");
 
            p1.setCount("2");
            p1.setStatus("service 1");
            //
            Person p2 = new Person();
 
            p2.setCasta("BETA");
       
            p2.setCount("56");
            p2.setStatus("treatment");
 
            personDAO.save(p1);
            personDAO.save(p2);
        }
 
    }
     
}
