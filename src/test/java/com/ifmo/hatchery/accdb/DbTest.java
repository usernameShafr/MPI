package com.ifmo.hatchery.accdb;

import com.ifmo.hatchery.model.auth.UserX;
import com.ifmo.hatchery.model.system.BiomaterialType;
import com.ifmo.hatchery.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.HttpURLConnection;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class DbTest {
    private UserRepository<UserX, Long> userRepository;

    @Test
    public void testLastnameAndID() {
        UserX user = userRepository.findByUsername("admin");
        assertEquals("admin",user.getLastName());

    }
}
