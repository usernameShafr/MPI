package com.ifmo.hatchery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class HatcheryApplication {

    public static void main(String[] args) {
        Objects.equals(null, null);

        SpringApplication.run(HatcheryApplication.class, args);
    }

}
