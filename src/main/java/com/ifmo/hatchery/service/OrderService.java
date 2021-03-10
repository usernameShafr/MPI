package com.ifmo.hatchery.service;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.Caste;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.model.system.Skill;
import com.ifmo.hatchery.model.system.Stage;
import com.ifmo.hatchery.model.system.Task;
import com.ifmo.hatchery.repository.OrderRepository;
import com.ifmo.hatchery.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository<OrderX, Long> orderRepository;

    @Autowired
    private TaskRepository<Task, Long> taskRepository;


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

    public OrderX createOrder(Caste caste, List<Skill> skillsList, UserX customer) {
        OrderX order = new OrderX();
        order.setCaste(caste);
        order.setSkills(skillsList);
        order.setCustomer(customer);

        return createTaskAndOrder(order);
    }

    @Transactional
    private OrderX createTaskAndOrder(OrderX order) {
        order = orderRepository.save(order);
        Task task = new Task();
        task.setStage(Stage.FERTILIZATION);
        task.setOrder(order);
        task = taskRepository.save(task);
        order.setTask(task);
        return orderRepository.save(order);
    }

}
