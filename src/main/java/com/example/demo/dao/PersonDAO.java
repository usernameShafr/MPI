package com.example.demo.dao;


import java.util.List;
 
import com.example.demo.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface PersonDAO extends CrudRepository<Person, Long> {
 
    public List<Person> findByCastaLike(String casta);
    public List<Person> findByCountLike(String count);
    
 
    
 
}