package com.ifmo.hatchery.db;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.BioState;
import com.ifmo.hatchery.model.system.Biomaterial;
import com.ifmo.hatchery.model.system.BiomaterialType;
import com.ifmo.hatchery.model.system.Caste;
import com.ifmo.hatchery.model.system.OrderX;
import com.ifmo.hatchery.repository.BiomaterialRepository;
import com.ifmo.hatchery.repository.OrderRepository;
import com.ifmo.hatchery.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DbTest {
    @Autowired
    private BiomaterialRepository<Biomaterial, Long> biomaterialRepository;

    @Autowired
    private OrderRepository<OrderX, Long> orderRepository;

    @Autowired
    private UserRepository<UserX, Long> userRepository;

    @Test
    public void biomaterialTest() {
        Biomaterial biomaterial = new Biomaterial();
        biomaterial.setBioState(BioState.NOT_USE);
        biomaterial.setType(BiomaterialType.MALE);
        biomaterial.setDonor(null);
        biomaterial = biomaterialRepository.save(biomaterial);
        assertNotNull(biomaterial.getId());
    }

    @Test
    public void orderTest() {
        OrderX order = new OrderX();
        order.setCaste(Caste.ALPHA);
        order.setSkills(Collections.emptyList());
        order.setCustomer(null);

        order = orderRepository.save(order);
        assertNotNull(order.getId());
    }

    @Test
    public void searchUserTest() {
        UserX admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        assertNotNull(admin.getId());
        assertEquals(admin.getUsername(), "admin");
    }
}
