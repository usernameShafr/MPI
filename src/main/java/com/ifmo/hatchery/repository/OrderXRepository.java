package com.ifmo.hatchery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ifmo.hatchery.model.system.OrderX;

public interface OrderXRepository<T extends OrderX, ID extends Long> extends JpaRepository<OrderX, Long>{

}
