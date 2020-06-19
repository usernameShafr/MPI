package com.example.demo.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

 
@Entity
@Table(name = "PERSON")
public class Person {
 
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
 
    @Column(name = "Casta", length = 64, nullable = false)
    private String casta;
 
    
    @Column(name = "Count", length = 64, nullable = false)
    private String count;
    
    @Column(name = "Status", length = 64, nullable = false)
    private String status;
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getCasta() {
        return casta;
    }
 
    public void setCasta(String casta) {
        this.casta = casta;
    }
 
    public String getCount() {
        return count;
    }
 
    public void setCount(String count) {
        this.count = count;
    }
    
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
}