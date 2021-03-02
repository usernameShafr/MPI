package com.ifmo.hatchery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ifmo.hatchery.model.system.OrderX;

import java.util.List;

public interface OrderXRepository extends JpaRepository<OrderX, Long>{

}
