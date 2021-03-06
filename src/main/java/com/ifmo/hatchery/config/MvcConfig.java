package com.ifmo.hatchery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("");
        registry.addViewController("/").setViewName("/");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/order").setViewName("order");
        registry.addViewController("/personList").setViewName("personList");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/donor").setViewName("donor");
        registry.addViewController("/service/ferliz").setViewName("service/ferliz");
        registry.addViewController("/restartService").setViewName("restartService");
        registry.addViewController("/error").setViewName("error");
    }

}