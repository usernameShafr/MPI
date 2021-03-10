package com.ifmo.hatchery.service;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.repository.OrderXRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderXService {
    @Autowired
    private OrderXRepository<OrderX, Long> orderRepository;


    public List<OrderX> findByCustomer(UserX customer){
        List<OrderX> orderX = orderRepository.findByCustomer(customer);
        return orderX;
    }

    public List<OrderX> findAll(){
        List<OrderX> orderX = orderRepository.findAll();
        return orderX;
    }

    public OrderX saveOrder (OrderX order) {
        order = orderRepository.save(order);
        return order;
    }

}
