package com.ifmo.hatchery.repository;


import com.ifmo.hatchery.model.auth.UserX;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ifmo.hatchery.model.system.OrderX;

import java.util.List;

public interface OrderRepository<T extends OrderX, ID extends Long> extends JpaRepository<OrderX, Long>{
    List<OrderX> findByCustomer(UserX customer);
}
